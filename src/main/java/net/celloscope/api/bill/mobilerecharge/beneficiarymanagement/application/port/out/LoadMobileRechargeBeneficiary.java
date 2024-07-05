package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;


import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.Optional;

public interface LoadMobileRechargeBeneficiary {
    Optional<MobileRechargeBeneficiary> getBeneficiary(String userId, String beneficiaryAccountNo);
    Optional<MobileRechargeBeneficiary> getBeneficiaryByOid(String beneficiaryOid);
}
