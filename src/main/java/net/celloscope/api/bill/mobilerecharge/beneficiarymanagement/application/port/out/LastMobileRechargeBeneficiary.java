package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;

import java.util.Optional;

public interface LastMobileRechargeBeneficiary {
    Optional<String> loadFromMobileRechargeService(String userId);

}
