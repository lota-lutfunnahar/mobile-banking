package net.celloscope.api.changePasswordWithOTP.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.changePasswordWithOTP.application.port.out.OtpPort;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.client.PinServerClient;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static net.celloscope.api.core.util.Messages.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class OtpAdapter implements OtpPort {

    private final PinServerClient pinServerClient;

    private final ChangePasswordPersistenceAdapter changePasswordPersistenceAdapter;

    @Override
    public String sendOtp(String requestId, String requestTimeoutInSeconds, String traceId, String refId, String mobileNo) throws ExceptionHandlerUtil {
        String offsetOtp = pinServerClient.sendOtpRequest(requestId, "", traceId, refId, mobileNo);
        return offsetOtp;
    }

    @Override
    public String verifyOtp(String requestId, String requestTimeoutInSeconds, String traceId, String otp, String uid, String offsetOtp) throws ExceptionHandlerUtil {
        String verifyOtpRequest = pinServerClient.sendVerifyOtpRequest(requestId, requestTimeoutInSeconds, traceId, otp, uid, offsetOtp);
         return verifyOtpRequest;
    }


}
