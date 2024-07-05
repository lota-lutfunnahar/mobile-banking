package net.celloscope.api.securityQuestion.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface ValidateSecurityQuestionUseCase {
    boolean validateSecurityQuestion(ValidateSecurityQuestionCommand command) throws ExceptionHandlerUtil;
}
