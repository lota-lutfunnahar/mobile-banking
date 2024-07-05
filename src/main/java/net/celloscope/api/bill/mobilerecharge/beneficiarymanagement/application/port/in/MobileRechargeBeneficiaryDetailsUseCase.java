package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryDetailsResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface MobileRechargeBeneficiaryDetailsUseCase {
    MobileRechargeBeneficiaryDetailsResponse getMobileRechargeBeneficiaryDetails(String beneficiaryOid) throws ExceptionHandlerUtil;
}
