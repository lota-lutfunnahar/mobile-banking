package net.celloscope.api.changePasswordWithOTP.application.port.out;

import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.entity.ChangePasswordJpaEntity;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface UpdateChangePasswordPort {

    boolean updateChangePassword(ChangePassword changePassword) throws ExceptionHandlerUtil;

}
