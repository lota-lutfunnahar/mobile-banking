package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.UpdateMobileRechargeBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.UpdateMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateMobileRechargeBeneficiaryService implements UpdateMobileRechargeBeneficiaryUseCase {
    private final UpdateMobileRechargeBeneficiary updateMobileRechargeBeneficiary;
    private final ModelMapper modelMapper;

    public UpdateMobileRechargeBeneficiaryService(UpdateMobileRechargeBeneficiary updateMobileRechargeBeneficiary, ModelMapper modelMapper) {
        this.updateMobileRechargeBeneficiary = updateMobileRechargeBeneficiary;
        this.modelMapper = modelMapper;
    }

    @Override
    public MobileRechargeBeneficiaryUpdateResponse updateBeneficiary(MobileRechargeBeneficiaryUpdateRequest request) throws ExceptionHandlerUtil {
        Optional<MobileRechargeBeneficiary> updatedBeneficiary = updateMobileRechargeBeneficiary.update(modelMapper.map(request, MobileRechargeBeneficiary.class));

        if (!updatedBeneficiary.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_ACCOUNT_NOT_FOUND);

        return MobileRechargeBeneficiaryUpdateResponse.builder()
                .beneficiaryOid(updatedBeneficiary.get().getBeneficiaryOid())
                .userMessage(Messages.BENEFICIARY_UPDATED)
                .build();
    }
}
