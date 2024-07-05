
package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.List;

public interface LoadBeneficiaryList {
    List<MobileRechargeBeneficiary> getBeneficiaryList(String userId);
}
