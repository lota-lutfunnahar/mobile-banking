package net.celloscope.api.restriction.application.port.out;

import net.celloscope.api.restriction.adapter.out.persistence.BeneficiarySearchRestriction;

public interface LoadUserRestrictionPort {
    BeneficiarySearchRestriction get(String userId);
}
