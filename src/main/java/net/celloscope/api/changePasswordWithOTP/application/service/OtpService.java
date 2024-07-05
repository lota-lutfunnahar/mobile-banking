package net.celloscope.api.changePasswordWithOTP.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.ChangePasswordPersistenceAdapter;
import net.celloscope.api.changePasswordWithOTP.application.port.in.ReSendOTPCommand;
import net.celloscope.api.changePasswordWithOTP.application.port.in.ReSendOTPUseCase;
import net.celloscope.api.changePasswordWithOTP.application.port.in.VerifyOTPCommand;
import net.celloscope.api.changePasswordWithOTP.application.port.in.VerifyOtpUseCase;
import net.celloscope.api.changePasswordWithOTP.application.port.out.LoadChangePasswordPort;
import net.celloscope.api.changePasswordWithOTP.application.port.out.OtpPort;
import net.celloscope.api.changePasswordWithOTP.application.port.out.UpdateChangePasswordPort;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.metaProperty.service.MetaPropertyService;
import net.celloscope.api.mobileVerificationForAccountCreation.entity.OtpVerificationTrailEntity;
import net.celloscope.api.mobileVerificationForAccountCreation.repository.OtpVerificationTrailRepository;
import net.celloscope.api.passwordRecovery.enums.TrailStatus;
import net.celloscope.api.passwordresetlog.application.port.in.PasswordResetUseCase;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import net.celloscope.api.user.entity.IbUserEntity;
import net.celloscope.api.user.service.IbUserService;
import org.slf4j.MDC;
import net.celloscope.api.user.dto.ChangePasswordRequest;
import net.celloscope.api.user.entity.IbUserEntity;
import net.celloscope.api.user.respository.IbUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OtpService implements VerifyOtpUseCase , ReSendOTPUseCase {

    private final OtpPort otpPort;
    private final LoadChangePasswordPort loadChangePasswordPort;
    private final UpdateChangePasswordPort updateChangePasswordPort;
    private final IbUserService ibUserService;
    private final ChangePasswordPersistenceAdapter changePasswordPersistenceAdapter;
    private final MetaPropertyService metaPropertyService;// TODO: ২৭/১/২২  need to be in clean architecture
    private final IbUserRepository ibUserRepository;
    private final PasswordResetUseCase passwordResetUseCase;



    private final OtpVerificationTrailRepository otpVerificationTrailRepository;  // TODO: ২৭/১/২২  need to be in clean architecture

    private static final String StatusNotOtpSent = "Password Change status is not otp sent";
    private static final String InvalidOtp = "Otp not valid";

    @Override
    public boolean verifyOTP(VerifyOTPCommand command, String requestId, String userId,
                             String requestTimeoutInSeconds, String traceId) throws ExceptionHandlerUtil {

        IbUserEntity userDetail = checkIfUserExists(userId);

        checkStatusForExistingUser(userDetail);

        ChangePassword changePasswordEntity = loadChangePasswordPort.loadChangePasswordForUserByRefId(command.getRefId());
        checkIfOTPSend(changePasswordEntity);
        checkInvalidOTPRequestCount(changePasswordEntity);
        log.info("sending otp verification request for RequestId: {} and TraceId: {}",requestId,traceId);
        String otpVerified = otpPort.verifyOtp(requestId, requestTimeoutInSeconds, traceId, command.getOtp(),
                changePasswordEntity.getRefId(), changePasswordEntity.getOffsetOtp());

        saveOtpVerificationTrailData(changePasswordEntity.getRefId(), changePasswordEntity.getIbUserOid(), INVALID);

        if(otpVerified == null || !otpVerified.equalsIgnoreCase("valid")){
            updateChangePasswordFailStatus(changePasswordEntity, InvalidOtp, changePasswordEntity.getStatus());
            log.info("OTP Verification Failed  for RequestId: {} and TraceId: {} and referenceId: {}", requestId, traceId, command.getRefId());
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, InvalidOtp);
        }
        updateChangePasswordSuccessStatus(changePasswordEntity);
        savePasswordResetLog(userId, userDetail.getPassword(), changePasswordEntity.getGivenPassword());
        updatePasswordToDb(changePasswordEntity.getRefId(),userDetail);
        return true;
    }


    @Override
    public Map<String, Object> reSendOTP(String userId, ReSendOTPCommand command) throws ExceptionHandlerUtil {
        IbUserEntity ibUser = ibUserService.checkIfUserExists(userId);
        ChangePassword changePasswordEntity = loadChangePasswordPort.loadChangePasswordForUserByRefId(command.getRefId());
        checkIfOTPSend(changePasswordEntity);
        checkOTPResendCount(changePasswordEntity);
        log.info("sending OTP for referenceId: {}",command.getRefId());
        String offsetOTP = otpPort.sendOtp(changePasswordEntity.getRefId(), "", MDC.get("traceId"), changePasswordEntity.getRefId(), ibUser.getMobileNo());
        if (offsetOTP == null) {
            updateChangePasswordFailStatus(changePasswordEntity, OTP_NOT_SENT, FAILED);
            log.info("OTP send Failed for referenceId: {}",command.getRefId());
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, OTP_NOT_SENT);
        }
        log.info("Otp sent successfully");
        updateResendSuccess(offsetOTP, changePasswordEntity.getRefId());
        Map<String, Object> response = buildOTPResendResponse(String.valueOf(metaPropertyService.getTimeToLiveOtp()), changePasswordEntity.getRefId());
        return response;
    }


    private Boolean savePasswordResetLog(String userId, String oldPassword, String newPassword) throws ExceptionHandlerUtil {
        PasswordResetLog passwordResetLog = passwordResetUseCase.createNewPasswordResetForUser(userId, oldPassword, newPassword);
        if (passwordResetLog == null) {
            log.error("Failed to save PasswordResetLog in db for UserId: {}", userId);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error!");
        } else
            return true;
    }



    private boolean updatePasswordToDb(String refId,IbUserEntity existIbUserEntity) throws ExceptionHandlerUtil {
        ChangePassword changePassword = loadChangePasswordPort.loadChangePasswordForUserByRefId(refId);
        existIbUserEntity.setPassword(changePassword.getGivenPassword());
        existIbUserEntity.setResetRequired(No);
        existIbUserEntity.setEditedBy(changePassword.getIbUserOid());
        existIbUserEntity.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        IbUserEntity ibUserEntity = ibUserRepository.save(existIbUserEntity);
        if (ibUserEntity.getUserId() == null) {
            log.error("failed to update password");
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error!");
        }
        log.info("Password updated successfully for userId: {}",ibUserEntity.getUserId());
        return true;
    }

    private void updateChangePasswordSuccessStatus(ChangePassword changePasswordEntity) throws ExceptionHandlerUtil {
        changePasswordEntity.setStatus("OtpVerified");
        changePasswordEntity.setOtpVerifiedOn(Timestamp.valueOf(LocalDateTime.now()));
        updateChangePasswordPort.updateChangePassword(changePasswordEntity);
    }

    private void checkInvalidOTPRequestCount(ChangePassword changePasswordEntity) throws ExceptionHandlerUtil {
        int invalidCount = otpVerificationTrailRepository.countByReferenceOidAndStatusContainingIgnoreCase(changePasswordEntity.getRefId(), INVALID);
        if (invalidCount >= metaPropertyService.getInvalidOtpRetryCount()) {
            updateChangePasswordFailStatus(changePasswordEntity, REQUEST_OTP_INVALID_MULTIPLE_TIMES, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, REQUEST_OTP_INVALID_MULTIPLE_TIMES);
        }
    }


    private boolean checkOTPResendCount(ChangePassword changePasswordEntity) throws ExceptionHandlerUtil {
       if(!(changePasswordEntity.getOtpResentCount() < metaPropertyService.getOtpResendLimit())){
            updateChangePasswordFailStatus(changePasswordEntity, RESEND_OTP_LIMIT_EXCEEDED, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, RESEND_OTP_LIMIT_EXCEEDED);
        }
        return true;
    }


    private Map<String, Object> buildOTPResendResponse(String timeToLiveOtp, String refId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userMessage", "Request Successfully processed");
        response.put("refId", refId);
        response.put("otpValidTimeInSec", timeToLiveOtp);
        return response;
    }

    private boolean updateResendSuccess(String offsetOtp, String refId) throws ExceptionHandlerUtil {
        ChangePassword changePassword = changePasswordPersistenceAdapter.loadChangePasswordForUserByRefId(refId);
        changePassword.setOffsetOtp(offsetOtp);
        changePassword.setOtpSentOn(Timestamp.valueOf(LocalDateTime.now()));
        changePassword.setOtpResentCount(changePassword.getOtpResentCount()+1);
        changePassword.setStatus(OTP_SENT);
        return changePasswordPersistenceAdapter.updateChangePassword(changePassword);
    }

    public void updateChangePasswordFailStatus(ChangePassword entity, String failureReason, String status) throws ExceptionHandlerUtil {
        entity.setFailureReason(failureReason);
        entity.setStatus(status);
        updateChangePasswordPort.updateChangePassword(entity);
    }

    private void checkIfOTPSend(ChangePassword changePasswordEntity) throws ExceptionHandlerUtil {
        if(changePasswordEntity == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, DATA_NOT_FOUND);
        }
        if(!changePasswordEntity.getStatus().equalsIgnoreCase(TrailStatus.Otp_Sent.getValue())){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, StatusNotOtpSent);
        }
    }

    public void saveOtpVerificationTrailData(String refId, String userOid, String status) {
        OtpVerificationTrailEntity entity = OtpVerificationTrailEntity.builder()
                .referenceOid(refId)
                .ibUserOid(userOid)
                .requestTime(Timestamp.valueOf(LocalDateTime.now()))
                .status(status)
                .build();
        otpVerificationTrailRepository.save(entity);
    }

    public IbUserEntity checkIfUserExists(String userId) throws ExceptionHandlerUtil {
        IbUserEntity ibUserEntity = ibUserRepository.findByUserId(userId);
        if (ibUserEntity == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }
        log.info("user info found by userId {}", ibUserEntity);
        return ibUserEntity;
    }

    private boolean checkStatusForExistingUser(IbUserEntity userDetail) throws ExceptionHandlerUtil {
        List<String> allowedStatus = Arrays.asList(PASSWORD_CREATED, ACTIVE, WAITING_FOR_VERIFICATION, WAITING_FOR_APPROVAL);
        if (!allowedStatus.contains(userDetail.getStatus()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, USER_INACTIVE);
        else return true;
    }
}
