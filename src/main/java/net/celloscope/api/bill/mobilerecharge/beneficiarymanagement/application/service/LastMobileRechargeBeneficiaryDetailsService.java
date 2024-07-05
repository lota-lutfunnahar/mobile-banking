package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.LastMobileRechargeBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.LastMobileRechargeBeneficiaryResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LastMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class LastMobileRechargeBeneficiaryDetailsService implements LastMobileRechargeBeneficiaryUseCase {
    private final LoadIBUser loadIBUser;
    private final LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary;
    private final LastMobileRechargeBeneficiary lastMobileRechargeBeneficiary;

    public LastMobileRechargeBeneficiaryDetailsService(LoadIBUser loadIBUser, LoadMobileRechargeBeneficiary loadMobileRechargeBeneficiary, LastMobileRechargeBeneficiary lastMobileRechargeBeneficiary) {
        this.loadIBUser = loadIBUser;
        this.loadMobileRechargeBeneficiary = loadMobileRechargeBeneficiary;
        this.lastMobileRechargeBeneficiary = lastMobileRechargeBeneficiary;
    }

    @Override
    public LastMobileRechargeBeneficiaryResponse getLastMobileRechargeBeneficiary(String userId) throws ExceptionHandlerUtil {
        Optional<IBUserDomain> ibUserDomain = loadIBUser.getIBUser(userId);
        
        if (!ibUserDomain.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.USER_NOT_FOUND);

        log.info("Making call to Mobile Recharge service.");
        Optional<String> lastRechargedMobileNo = lastMobileRechargeBeneficiary.loadFromMobileRechargeService(userId);

        if (!lastRechargedMobileNo.isPresent()) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_LIST_NOT_FOUND);
        log.info("Last Recharge For this user id: {}", lastRechargedMobileNo.get());

        Optional<MobileRechargeBeneficiary> lastMobileRechargeBeneficiary = loadMobileRechargeBeneficiary.getBeneficiary(ibUserDomain.get().getIbUserOid(), lastRechargedMobileNo.get());

        if (!lastMobileRechargeBeneficiary.isPresent()) return LastMobileRechargeBeneficiaryResponse.builder()
                .userMessage(Messages.BENEFICIARY_ACCOUNT_NOT_FOUND)
                .data(new ArrayList<>())
                .build();
        log.info("Last recharged beneficiary: {}", lastMobileRechargeBeneficiary.get());

        return LastMobileRechargeBeneficiaryResponse.builder()
                .userMessage(Messages.BENEFICIARY_LIST_RETREIVED_SUCCESSFULLY)
                .data(Collections.singletonList(lastMobileRechargeBeneficiary.get()))
                .build();

    }
}
