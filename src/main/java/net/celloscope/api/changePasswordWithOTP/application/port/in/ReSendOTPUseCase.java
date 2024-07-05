package net.celloscope.api.changePasswordWithOTP.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.Map;

public interface ReSendOTPUseCase {
    Map<String, Object> reSendOTP(String userId, ReSendOTPCommand reSendOTPCommand) throws ExceptionHandlerUtil;
}
