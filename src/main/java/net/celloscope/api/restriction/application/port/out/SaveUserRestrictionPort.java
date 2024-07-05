package net.celloscope.api.restriction.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.restriction.adapter.out.persistence.BeneficiarySearchRestriction;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;

public interface SaveUserRestrictionPort {
    boolean save(BeneficiarySearchRestriction beneficiarySearchRestriction) ;

}
