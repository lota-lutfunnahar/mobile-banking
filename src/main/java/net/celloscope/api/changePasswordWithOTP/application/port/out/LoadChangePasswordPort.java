package net.celloscope.api.changePasswordWithOTP.application.port.out;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;


public interface LoadChangePasswordPort {
    ChangePassword loadChangePasswordForUserByChangePassOid(String changePassOid) throws ExceptionHandlerUtil;

    ChangePassword loadChangePasswordForUserByRefId(String refId) throws ExceptionHandlerUtil;
}
