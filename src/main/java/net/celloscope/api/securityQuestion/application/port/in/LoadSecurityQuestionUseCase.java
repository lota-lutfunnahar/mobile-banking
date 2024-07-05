package net.celloscope.api.securityQuestion.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoadSecurityQuestionUseCase {
   List<QuestionWithoutAnswer> loadSecurityQuestionForUser(String ibUserOid) throws ExceptionHandlerUtil;
}
