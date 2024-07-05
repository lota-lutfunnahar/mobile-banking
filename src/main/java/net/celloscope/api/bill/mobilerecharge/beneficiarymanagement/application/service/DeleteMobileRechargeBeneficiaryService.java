package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.DeleteMobileRechargeBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryDeleteResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.DeleteMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteMobileRechargeBeneficiaryService implements DeleteMobileRechargeBeneficiaryUseCase {
    private final DeleteMobileRechargeBeneficiary deleteMobileRechargeBeneficiary;

    public DeleteMobileRechargeBeneficiaryService(DeleteMobileRechargeBeneficiary deleteMobileRechargeBeneficiary) {
        this.deleteMobileRechargeBeneficiary = deleteMobileRechargeBeneficiary;
    }

    @Override
    public MobileRechargeBeneficiaryDeleteResponse deleteBeneficiary(String beneficiaryOid) throws ExceptionHandlerUtil{
        Optional<MobileRechargeBeneficiary> mobileRechargeBeneficiary = deleteMobileRechargeBeneficiary.delete(beneficiaryOid);

        if (!mobileRechargeBeneficiary.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_ACCOUNT_NOT_FOUND);

        return MobileRechargeBeneficiaryDeleteResponse.builder()
                .userMessage(Messages.BENEFICIARY_REMOVED)
                .build();
    }
}
