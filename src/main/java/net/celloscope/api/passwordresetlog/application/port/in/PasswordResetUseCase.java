package net.celloscope.api.passwordresetlog.application.port.in;

import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface PasswordResetUseCase {
    Boolean isNewPasswordMatchedWithLastFewPassword(String userId, String newPassword);
    PasswordResetLog createNewPasswordResetForUser(String userId, String oldPassword, String newPassword);
}
