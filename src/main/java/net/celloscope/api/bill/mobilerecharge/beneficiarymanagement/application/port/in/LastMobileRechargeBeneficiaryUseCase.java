package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.LastMobileRechargeBeneficiaryResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface LastMobileRechargeBeneficiaryUseCase {
    LastMobileRechargeBeneficiaryResponse getLastMobileRechargeBeneficiary(String userId) throws ExceptionHandlerUtil;
}
