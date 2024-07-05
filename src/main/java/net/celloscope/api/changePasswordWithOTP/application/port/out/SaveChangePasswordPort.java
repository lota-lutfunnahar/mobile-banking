package net.celloscope.api.changePasswordWithOTP.application.port.out;

import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface SaveChangePasswordPort {
    boolean saveChangePasswordForUser(ChangePassword changePassword) throws ExceptionHandlerUtil;
}
