package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;

import java.util.Optional;

public interface LoadIBUser {
    Optional<IBUserDomain> getIBUser(String userId);
}
