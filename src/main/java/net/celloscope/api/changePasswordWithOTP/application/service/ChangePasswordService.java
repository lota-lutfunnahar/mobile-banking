package net.celloscope.api.changePasswordWithOTP.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.ChangePasswordPersistenceAdapter;
import net.celloscope.api.changePasswordWithOTP.application.port.in.ChangePasswordUseCase;
import net.celloscope.api.changePasswordWithOTP.application.port.out.OtpPort;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.metaProperty.service.MetaPropertyService;
import net.celloscope.api.passwordresetlog.application.port.in.PasswordResetUseCase;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import net.celloscope.api.user.dto.ChangePasswordRequest;
import net.celloscope.api.user.entity.IbUserEntity;
import net.celloscope.api.user.respository.IbUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final PasswordEncoder passwordEncoder;

    private final IbUserRepository ibUserRepository;

    private final PasswordResetUseCase passwordResetUseCase;

    private final OtpPort otpPort;

    private final MetaPropertyService metaPropertyService;

    private final ChangePasswordPersistenceAdapter changePasswordPersistenceAdapter;


    @Value("${otp.required}")
    boolean otpRequired;



    @Override
    public Map<String, Object> updatePassword(ChangePasswordRequest request, String userId, String requestId, String traceId) throws ExceptionHandlerUtil {
        IbUserEntity userDetail = checkIfUserExists(userId);

        checkStatusForExistingUser(userDetail);

        checkOldPasswordSanity(request.getOldPassword(), userDetail.getPassword());

        checkNewPasswordSanity(request.getNewPassword(), userDetail.getPassword());

        checkPasswordPolicy(request, userId, userDetail);

        int timeToLiveOtp = metaPropertyService.getTimeToLiveOtp();

        String refId = UUID.randomUUID().toString();

        if (otpRequired) {
            saveChangePasswordRequest(requestId, request.getNewPassword(), traceId, refId, userDetail.getIbUserOid());
            log.info("sending OTP for requestId: {} and tracId: {} and referenceId: {}", requestId, traceId, refId);
            String offsetOTP = otpPort.sendOtp(requestId, "", traceId, refId, userDetail.getMobileNo());
            if (offsetOTP == null) {
                updateChangePasswordFailed(FAILED, refId);
                log.info("OTP send Failed for referenceId: {}", refId);
                throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, OTP_NOT_SENT);
            }
            log.info("Otp sent successfully");
            updateChangePasswordSuccess(offsetOTP, refId);

            return buildOTPTrueResponse(String.valueOf(timeToLiveOtp), refId);

        } else {
            savePasswordResetLog(userId, userDetail.getPassword(), passwordEncoder.encode(request.getNewPassword()));
            updatePasswordToDb(request, userId, userDetail);
            return buildOTPFalseResponse();
        }

    }

    private boolean checkStatusForExistingUser(IbUserEntity userDetail) throws ExceptionHandlerUtil {
        List<String> allowedStatus = Arrays.asList(PASSWORD_CREATED, ACTIVE, WAITING_FOR_VERIFICATION, WAITING_FOR_APPROVAL);
        if (!allowedStatus.contains(userDetail.getStatus()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, USER_INACTIVE);
        else return true;
    }


    public IbUserEntity checkIfUserExists(String userId) throws ExceptionHandlerUtil {
        IbUserEntity ibUserEntity = ibUserRepository.findByUserId(userId);
        if (ibUserEntity == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }
        log.info("user info found by userId {}", ibUserEntity);
        return ibUserEntity;
    }



    private boolean checkOldPasswordSanity(String givenOldPassword, String userCurrentPassword) throws ExceptionHandlerUtil {
        if (!passwordEncoder.matches(givenOldPassword, userCurrentPassword)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, OLD_PASSWORD_DOES_NOT_MATCH);
        }
        else return true;
    }

    private boolean checkNewPasswordSanity(String givenNewPassword, String userCurrentPassword) throws ExceptionHandlerUtil {
        if (passwordEncoder.matches(givenNewPassword, userCurrentPassword)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, NEW_PASSWORD_MATCH_WITH_CURRENT_PASSWORD);
        }
        else return true;
    }

    private boolean checkPasswordPolicy(ChangePasswordRequest request, String userId, IbUserEntity userDetail) throws ExceptionHandlerUtil {
        if (passwordResetUseCase.isNewPasswordMatchedWithLastFewPassword(userId, request.getNewPassword()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, OLD_PASSWORD_MATCH_WITH_NEW_PASSWORD);    // check appropriate message

        return true;
    }


    private Boolean savePasswordResetLog(String userId, String oldPassword, String newPassword) throws ExceptionHandlerUtil {
        PasswordResetLog passwordResetLog = passwordResetUseCase.createNewPasswordResetForUser(userId, oldPassword, newPassword);
        if (passwordResetLog == null) {
            log.error("Failed to save PasswordResetLog in db for UserId: {}", userId);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error!");
        } else
            return true;
    }

    private boolean updateChangePasswordFailed(String otpNotSent, String refId) throws ExceptionHandlerUtil {
        ChangePassword changePassword = changePasswordPersistenceAdapter.loadChangePasswordForUserByRefId(refId);
        changePassword.setStatus(otpNotSent);
        return changePasswordPersistenceAdapter.updateChangePassword(changePassword);
    }

    private boolean updateChangePasswordSuccess(String offsetOtp, String refId) throws ExceptionHandlerUtil {
        ChangePassword changePassword = changePasswordPersistenceAdapter.loadChangePasswordForUserByRefId(refId);
        changePassword.setOffsetOtp(offsetOtp);
        changePassword.setOtpSentOn(Timestamp.valueOf(LocalDateTime.now()));
        changePassword.setStatus(OTP_SENT);
        return changePasswordPersistenceAdapter.updateChangePassword(changePassword);
    }

    private boolean saveChangePasswordRequest(String requestId, String givenPass, String traceId, String refId, String ibUserOid) throws ExceptionHandlerUtil {
        String encodedNewPass = passwordEncoder.encode(givenPass);
        return changePasswordPersistenceAdapter.saveChangePasswordForUser(
                ChangePassword
                        .builder()
                        .requestId(requestId)
                        .traceId(traceId)
                        .givenPassword(encodedNewPass)
                        .ibUserOid(ibUserOid)
                        .refId(refId)
                        .build());
    }

    private boolean updatePasswordToDb(ChangePasswordRequest request, String userId, IbUserEntity userDetail) throws ExceptionHandlerUtil {
        String hashedPassword = passwordEncoder.encode(request.getNewPassword());
        userDetail.setPassword(hashedPassword);
        userDetail.setResetRequired(No);
        userDetail.setEditedBy(userId);
        userDetail.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        IbUserEntity ibUserEntity = ibUserRepository.save(userDetail);
        if (ibUserEntity.getUserId() == null) {
            log.error("failed to update password");
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error!");
        }
        log.info("Password updated successfully for userId: {}", ibUserEntity.getUserId());
        return true;
    }

    private Map<String, Object> buildOTPTrueResponse(String timeToLiveOtp, String refId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userMessage", "Request Successfully processed");
        response.put("otpRequired", true);
        response.put("refId", refId);
        response.put("otpValidTimeInSec", timeToLiveOtp);
        return response;
    }

    private Map<String, Object> buildOTPFalseResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("userMessage", PASSWORD_CHANGED_SUCCESSFULLY);
        response.put("otpRequired", false);
        response.put("refId", "");
        response.put("otpValidTimeInSec", "0");
        return response;
    }




}
