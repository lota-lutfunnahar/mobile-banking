package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.AddBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.AddMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddBeneficiaryService implements AddBeneficiaryUseCase {

    private final LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary;
    private final AddMobileRechargeBeneficiary addMobileRechargeBeneficiary;
    private final LoadIBUser loadIBUser;
    private final ModelMapper modelMapper;

    public AddBeneficiaryService(LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary, AddMobileRechargeBeneficiary addMobileRechargeBeneficiary, LoadIBUser loadIBUser, ModelMapper modelMapper) {
        this.loadMobileRechargeBeneficiary = loadMobileRechargeBeneficiary;
        this.addMobileRechargeBeneficiary = addMobileRechargeBeneficiary;
        this.loadIBUser = loadIBUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddBeneficiaryResponse addBeneficiary(AddBeneficiaryRequest addBeneficiaryRequest, String userId) throws ExceptionHandlerUtil{
        Optional<IBUserDomain> ibUser = loadIBUser.getIBUser(userId);

        if (!ibUser.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.USER_NOT_FOUND);

        Optional<MobileRechargeBeneficiary> existedBeneficiary = loadMobileRechargeBeneficiary.getBeneficiary(ibUser.get().getIbUserOid(), addBeneficiaryRequest.getBeneficiaryAccountNo());

        if (existedBeneficiary.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.BENEFICIARY_EXIST);

        Optional<MobileRechargeBeneficiary> savedBeneficiary = addMobileRechargeBeneficiary.saveBeneficiary(mapToMobileRechargeBeneficiary(addBeneficiaryRequest, ibUser.get().getIbUserOid()));

        if (!savedBeneficiary.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, Messages.BENEFICIARY_SAVED_FAILED);

        return buildResponse(savedBeneficiary.get());
    }

    private MobileRechargeBeneficiary mapToMobileRechargeBeneficiary(AddBeneficiaryRequest request, String userId){
        MobileRechargeBeneficiary beneficiary = modelMapper.map(request, MobileRechargeBeneficiary.class);
        beneficiary.setUserId(userId);
        return beneficiary;
    }

    private AddBeneficiaryResponse buildResponse(MobileRechargeBeneficiary beneficiary){
        return AddBeneficiaryResponse.builder()
                .beneficiaryOid(beneficiary.getBeneficiaryOid())
                .userMessage(Messages.BENEFICIARY_ADDED)
                .build();
    }

}
