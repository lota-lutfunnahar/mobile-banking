package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface BeneficiaryListUseCase {
    BeneficiaryListResponse getBeneficiaries(BeneficiaryListRequest request) throws ExceptionHandlerUtil;
}
