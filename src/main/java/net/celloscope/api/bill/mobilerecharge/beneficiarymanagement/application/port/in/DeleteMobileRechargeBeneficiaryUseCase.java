package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryDeleteResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface DeleteMobileRechargeBeneficiaryUseCase {
    MobileRechargeBeneficiaryDeleteResponse deleteBeneficiary(String beneficiaryOid) throws ExceptionHandlerUtil;
}
