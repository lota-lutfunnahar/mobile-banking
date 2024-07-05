package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface AddBeneficiaryUseCase {

    AddBeneficiaryResponse addBeneficiary(AddBeneficiaryRequest addBeneficiaryRequest, String userId) throws ExceptionHandlerUtil;
}
