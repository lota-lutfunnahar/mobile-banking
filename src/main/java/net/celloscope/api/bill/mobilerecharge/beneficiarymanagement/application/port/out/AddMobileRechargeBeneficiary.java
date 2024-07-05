package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.Optional;

public interface AddMobileRechargeBeneficiary {
    Optional<MobileRechargeBeneficiary> saveBeneficiary(MobileRechargeBeneficiary beneficiary);


}
