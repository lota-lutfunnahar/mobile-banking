package net.celloscope.api.passwordresetlog.application.port.out;

import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;

import java.util.List;

public interface PasswordResetLogPersistence {
    public List<PasswordResetLog> getLastPasswordResetLogsByUserIdAndCountFromPersistence(String userID, int count);
    public PasswordResetLog saveNewPasswordResetLogInPersistence(PasswordResetLog passwordResetLog);
}
