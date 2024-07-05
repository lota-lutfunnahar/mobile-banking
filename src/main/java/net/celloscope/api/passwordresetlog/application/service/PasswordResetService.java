package net.celloscope.api.passwordresetlog.application.service;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.passwordresetlog.application.port.in.PasswordResetUseCase;
import net.celloscope.api.passwordresetlog.application.port.out.PasswordResetLogPersistence;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PasswordResetService implements PasswordResetUseCase {

    private final PasswordResetLogPersistence passwordResetLogPersistence;
    private final PasswordEncoder passwordEncoder;
    @Value("${password-reset-log.match-with-last-used-password.count}")
    private int passwordResetLogCount;

    public PasswordResetService(PasswordResetLogPersistence passwordResetLogPersistence, PasswordEncoder passwordEncoder) {
        this.passwordResetLogPersistence = passwordResetLogPersistence;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isNewPasswordMatchedWithLastFewPassword(String userId, String newPassword) {
        return getLastFewPasswordsByUserIDAndCount(userId, passwordResetLogCount)
                .stream()
                .anyMatch(s -> passwordEncoder.matches(newPassword, s));
    }

    @Override
    public PasswordResetLog createNewPasswordResetForUser(String userId, String oldPassword, String newPassword) {
        PasswordResetLog passwordResetLog =  passwordResetLogPersistence.saveNewPasswordResetLogInPersistence(new PasswordResetLog(userId, oldPassword, newPassword));
        log.info("Password Reset Log is saved {}", passwordResetLog);
        return passwordResetLog;
    }

    public List<String> getLastFewPasswordsByUserIDAndCount(String userId, int count) {
        List<String> list =  passwordResetLogPersistence.getLastPasswordResetLogsByUserIdAndCountFromPersistence(userId, count)
                .stream()
                .map(PasswordResetLog::getNewPassword)
                .collect(Collectors.toList());
        log.info("Last few password for user {}", userId);
        log.info("are {}", list);
        return list;
    }
}
