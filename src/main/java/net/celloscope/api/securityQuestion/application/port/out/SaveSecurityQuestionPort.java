package net.celloscope.api.securityQuestion.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;

public interface SaveSecurityQuestionPort {
    boolean saveSecurityQuestionForUser(SaveSecurityQuestionCommand saveSecurityQuestionCommand) throws ExceptionHandlerUtil;

}
