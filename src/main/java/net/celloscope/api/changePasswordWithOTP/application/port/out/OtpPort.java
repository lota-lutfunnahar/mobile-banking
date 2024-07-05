package net.celloscope.api.changePasswordWithOTP.application.port.out;


import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface OtpPort {
    String sendOtp(String requestId, String requestTimeoutInSeconds, String traceId, String refId, String mobileNo) throws ExceptionHandlerUtil;

    String verifyOtp(String requestId, String requestTimeoutInSeconds, String traceId, String otp,
                   String uid, String offsetOtp) throws ExceptionHandlerUtil;

}
