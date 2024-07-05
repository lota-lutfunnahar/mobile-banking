package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadBeneficiaryList;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoadBeneficiaryListService implements BeneficiaryListUseCase {

    private final LoadBeneficiaryList loadBeneficiaryList;
    private final LoadIBUser loadIBUser;

    public LoadBeneficiaryListService(LoadBeneficiaryList loadBeneficiaryList, LoadIBUser loadIBUser) {
        this.loadBeneficiaryList = loadBeneficiaryList;
        this.loadIBUser = loadIBUser;
    }

    @Override
    public BeneficiaryListResponse getBeneficiaries(BeneficiaryListRequest request) throws ExceptionHandlerUtil{
        Optional<IBUserDomain> ibUser = loadIBUser.getIBUser(request.getUserId());

        if (!ibUser.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.USER_NOT_FOUND);

        request.setUserId(ibUser.get().getIbUserOid());

        List<MobileRechargeBeneficiary> beneficiaries = loadBeneficiaryList.getBeneficiaryList(request.getUserId());

        if (beneficiaries == null || beneficiaries.isEmpty()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_LIST_NOT_FOUND);

        return buildBeneficiaryListResponse(beneficiaries);
    }

    private BeneficiaryListResponse buildBeneficiaryListResponse(List<MobileRechargeBeneficiary> beneficiaries){
        return BeneficiaryListResponse.builder()
                .userMessage(Messages.BENEFICIARY_LIST_RETREIVED_SUCCESSFULLY)
                .data(beneficiaries)
                .build();
    }
}
