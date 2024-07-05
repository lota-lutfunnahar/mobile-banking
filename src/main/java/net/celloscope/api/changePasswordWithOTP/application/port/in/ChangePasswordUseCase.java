package net.celloscope.api.changePasswordWithOTP.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.user.dto.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ChangePasswordUseCase {
    Map<String,Object> updatePassword(ChangePasswordRequest request, String userId, String requestID,String traceId) throws ExceptionHandlerUtil;
}
