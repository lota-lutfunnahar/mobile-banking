package net.celloscope.api.securityQuestion.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;

public interface SaveSecurityQuestionUseCase {
   boolean saveSecurityQuestionForUser(SaveSecurityQuestionCommand saveSecurityQuestionCommand) throws ExceptionHandlerUtil;
}
