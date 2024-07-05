package net.celloscope.api.securityQuestion.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;

import java.util.List;

public interface LoadSecurityQuestionPort {
    SecurityQuestion loadSecurityQuestionForUser(String userOid) throws ExceptionHandlerUtil;
}
