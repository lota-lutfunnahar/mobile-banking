package net.celloscope.api.securityQuestion.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface LoadSecurityQuestionVerificationTrailPort {
    int loadSecurityQuestionVerificationTrailForUser(String referenceOid) throws ExceptionHandlerUtil;

}
