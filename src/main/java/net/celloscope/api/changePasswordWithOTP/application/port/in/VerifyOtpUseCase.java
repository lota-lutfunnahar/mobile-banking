package net.celloscope.api.changePasswordWithOTP.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface VerifyOtpUseCase {
    boolean verifyOTP(VerifyOTPCommand command, String requestId, String userId,String requestTimeoutInSeconds,String traceId) throws ExceptionHandlerUtil;

}
