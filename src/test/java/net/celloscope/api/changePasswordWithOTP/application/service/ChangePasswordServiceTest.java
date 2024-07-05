package net.celloscope.api.changePasswordWithOTP.application.service;

import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.ChangePasswordPersistenceAdapter;
import net.celloscope.api.changePasswordWithOTP.application.port.out.OtpPort;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.passwordresetlog.application.port.in.PasswordResetUseCase;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import net.celloscope.api.user.dto.ChangePasswordRequest;
import net.celloscope.api.user.entity.IbUserEntity;
import net.celloscope.api.user.respository.IbUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

import static net.celloscope.api.core.util.Messages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ChangePasswordServiceTest {
    @Autowired
    private ChangePasswordService changePasswordService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private IbUserRepository ibUserRepository;

    @MockBean
    private ChangePasswordPersistenceAdapter changePasswordPersistenceAdapter;

    @MockBean
    PasswordResetUseCase passwordResetUseCase;

    @MockBean
    private OtpPort otpPort;


    @Test
    void contextLoad(){
        assertThat(changePasswordService).isNotNull();
    }

    @Test
    void givenValidChangePasswordRequest_returnsPasswordChangedResponse_whenOTPIsNotRequired() throws Exception{
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = false;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, request.getOldPassword(),Messages.ACTIVE));
        when(ibUserRepository.save(any())).thenReturn(buildActiveIbUserEntity(userId, request.getNewPassword(),Messages.ACTIVE));
        when(passwordResetUseCase.createNewPasswordResetForUser(any(), any(), anyString())).thenReturn(buildPasswordResetLog(request.getOldPassword(), request.getNewPassword(), userId));
        //then
        Map<String, Object> response = changePasswordService.updatePassword(request, userId, requestId, traceId);
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
        assertThat(response.get("otpRequired")).isEqualTo(Boolean.FALSE);
        assertThat(response.get("userMessage")).isEqualTo(Messages.PASSWORD_CHANGED_SUCCESSFULLY);
    }

    @Test
    void givenValidChangePasswordRequest_returnsOTPSentResponse_whenOTPIsRequired() throws Exception{
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        String offsetOtp = "12345";
        changePasswordService.otpRequired = true;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, request.getOldPassword(),Messages.ACTIVE));
        when(changePasswordPersistenceAdapter.saveChangePasswordForUser(any())).thenReturn(true);
        when(otpPort.sendOtp(anyString(),anyString(), anyString(), anyString(), anyString())).thenReturn(offsetOtp);
        when(changePasswordPersistenceAdapter.loadChangePasswordForUserByRefId(any())).thenReturn(buildChangePasswordObject());
        when(changePasswordPersistenceAdapter.updateChangePassword(any())).thenReturn(true);
        //then
        Map<String, Object> response = changePasswordService.updatePassword(request, userId, requestId, traceId);
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
        assertThat(response.get("otpRequired")).isEqualTo(Boolean.TRUE);
    }

    @Test
    void shouldReturn_OTP_NOT_SENT_whileOtpIsNullAndOTPIsRequired() throws Exception{
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = true;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, request.getOldPassword(),Messages.ACTIVE));
        when(changePasswordPersistenceAdapter.saveChangePasswordForUser(any())).thenReturn(true);
        when(otpPort.sendOtp(anyString(),anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        when(changePasswordPersistenceAdapter.loadChangePasswordForUserByRefId(any())).thenReturn(buildChangePasswordObject());
        when(changePasswordPersistenceAdapter.updateChangePassword(any())).thenReturn(true);
        //then
        ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
                () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

        assertEquals(OTP_NOT_SENT,exceptionHandlerUtil.getMessage());
    }

    @Test
    void shouldReturn_UserInactive_While_UserStatusIs_USER_INACTIVE() throws ExceptionHandlerUtil {
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = false;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, request.getOldPassword(), USER_INACTIVE));

        ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
                () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

        assertEquals(USER_INACTIVE,exceptionHandlerUtil.getMessage());
    }


    @Test
    void shouldReturn_USER_NOT_FOUND_While_UserNotFound() throws ExceptionHandlerUtil {
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = false;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(null);

        ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
                () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

        assertEquals(USER_NOT_FOUND,exceptionHandlerUtil.getMessage());
    }

   @Test
    void shouldReturn_OLD_PASSWORD_DOES_NOT_MATCH_while_OldPasswordDoesNotMatch(){
       //given
       ChangePasswordRequest request = buildChangePasswordRequest("abc","cba");
       String userId = UUID.randomUUID().toString();
       String requestId = UUID.randomUUID().toString();
       String traceId = UUID.randomUUID().toString();
       changePasswordService.otpRequired = false;
       //when
       when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, "ddb",Messages.ACTIVE));

       ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
               () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

       assertEquals(OLD_PASSWORD_DOES_NOT_MATCH,exceptionHandlerUtil.getMessage());
    }

    @Test
    void shouldReturn_NEW_PASSWORD_MATCH_WITH_CURRENT_PASSWORD_while_newPasswordMatchedWithCurrentPassword(){
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","abc");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = false;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, "abc",Messages.ACTIVE));

        ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
                () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

        assertEquals(NEW_PASSWORD_MATCH_WITH_CURRENT_PASSWORD,exceptionHandlerUtil.getMessage());
    }


    @Test
    void shouldReturn_OLD_PASSWORD_MATCH_WITH_NEW_PASSWORD_while_newPasswordMatchedWithCurrentPassword(){
        //given
        ChangePasswordRequest request = buildChangePasswordRequest("abc","dba");
        String userId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        changePasswordService.otpRequired = false;
        //when
        when(ibUserRepository.findByUserId(anyString())).thenReturn(buildActiveIbUserEntity(userId, "abc",Messages.ACTIVE));
        when(passwordResetUseCase.isNewPasswordMatchedWithLastFewPassword(anyString(),anyString())).thenReturn(true);

        ExceptionHandlerUtil exceptionHandlerUtil = assertThrows(ExceptionHandlerUtil.class,
                () -> changePasswordService.updatePassword(request, userId, requestId, traceId));

        assertEquals(OLD_PASSWORD_MATCH_WITH_NEW_PASSWORD,exceptionHandlerUtil.getMessage());
    }

//    passwordResetUseCase.isNewPasswordMatchedWithLastFewPassword(userId, request.getNewPassword())

    private ChangePasswordRequest buildChangePasswordRequest(String oldPassword,String newPassword){
        return ChangePasswordRequest.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
    }

    private IbUserEntity buildActiveIbUserEntity(String userId, String password,String status){
        return IbUserEntity.builder()
                .ibUserOid(userId)
                .userId(userId)
                .status(status)
                .password(passwordEncoder.encode(password))
                .mobileNo("123456789")
                .build();
    }

//    private IbUserEntity buildInActiveIbUserEntity(String userId, String password){
//        return IbUserEntity.builder()
//                .ibUserOid(userId)
//                .userId(userId)
//                .status(status)
//                .password(passwordEncoder.encode(password))
//                .mobileNo("123456789")
//                .build();
//    }

    private ChangePassword buildChangePasswordObject(){
        return ChangePassword.builder().build();
    }

    private PasswordResetLog buildPasswordResetLog(String oldPass, String newPass, String userId){
        return PasswordResetLog.builder()
                .oldPassword(oldPass)
                .newPassword(newPass)
                .userId(userId)
                .build();
    }


}