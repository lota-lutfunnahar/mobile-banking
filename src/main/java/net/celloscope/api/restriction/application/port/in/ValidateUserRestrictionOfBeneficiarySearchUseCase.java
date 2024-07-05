package net.celloscope.api.restriction.application.port.in;

public interface ValidateUserRestrictionOfBeneficiarySearchUseCase {

   boolean checkIfUserIsRestrictedForBeneficiarySearch(String userId);
}
