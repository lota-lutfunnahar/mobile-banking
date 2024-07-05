package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.MobileRechargeBeneficiaryDetailsUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryDetailsResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadMobileRechargeBeneficiaryDetailsService implements MobileRechargeBeneficiaryDetailsUseCase {
    private final LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary;
    private final ModelMapper modelMapper;

    public LoadMobileRechargeBeneficiaryDetailsService(LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary, ModelMapper modelMapper) {
        this.loadMobileRechargeBeneficiary = loadMobileRechargeBeneficiary;
        this.modelMapper = modelMapper;
    }

    @Override
    public MobileRechargeBeneficiaryDetailsResponse getMobileRechargeBeneficiaryDetails(String beneficiaryOid) throws ExceptionHandlerUtil {
        Optional<MobileRechargeBeneficiary> beneficiary = loadMobileRechargeBeneficiary.getBeneficiaryByOid(beneficiaryOid);

        if (!beneficiary.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_DETAILS_NOT_FOUND);

        return modelMapper.map(beneficiary.get(), MobileRechargeBeneficiaryDetailsResponse.class);
    }
}
