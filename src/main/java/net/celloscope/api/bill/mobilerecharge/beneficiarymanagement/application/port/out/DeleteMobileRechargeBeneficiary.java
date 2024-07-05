package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;


import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.Optional;

public interface DeleteMobileRechargeBeneficiary {
    Optional<MobileRechargeBeneficiary> delete(String beneficiaryOid);
}
