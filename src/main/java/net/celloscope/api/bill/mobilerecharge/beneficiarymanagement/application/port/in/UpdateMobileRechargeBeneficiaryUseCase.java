package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface UpdateMobileRechargeBeneficiaryUseCase {
    public MobileRechargeBeneficiaryUpdateResponse updateBeneficiary(MobileRechargeBeneficiaryUpdateRequest request) throws ExceptionHandlerUtil;
}
