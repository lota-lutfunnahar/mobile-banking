package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryRepository;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryDetailsResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class LoadMobileRechargeBeneficiaryDetailsServiceTest {
    @Autowired
    LoadMobileRechargeBeneficiaryDetailsService detailsService;

    @MockBean
    MobileRechargeBeneficiaryRepository beneficiaryRepository;

    @Test
    void contextLoad(){
        assertThat(detailsService).isNotNull();
    }

    @Test
    void givenBeneficiaryOid_returnsMobileRechargeBeneficiaryDetailsResponse() throws Exception{
        //given
        String beneficiaryOid = UUID.randomUUID().toString();
        //when
        when(beneficiaryRepository.findByMobileRechargeBeneficiaryOid(any())).thenReturn(Optional.of(buildMobileRechargeBeneficiaryEntity(beneficiaryOid)));
        //then
        MobileRechargeBeneficiaryDetailsResponse response = detailsService.getMobileRechargeBeneficiaryDetails(beneficiaryOid);
        assertThat(response).isNotNull();
        assertThat(response.getBeneficiaryOid()).isEqualTo(beneficiaryOid);
    }

    @Test
    void givenBeneficiaryOidThatIsNotPresent_throwsNotFoundExceptions() throws Exception{
        //given
        String beneficiaryOid = UUID.randomUUID().toString();
        //when
        when(beneficiaryRepository.findByMobileRechargeBeneficiaryOid(any())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> detailsService.getMobileRechargeBeneficiaryDetails(beneficiaryOid))
                .isInstanceOf(ExceptionHandlerUtil.class)
                .hasMessage(Messages.BENEFICIARY_DETAILS_NOT_FOUND);
    }

    private MobileRechargeBeneficiaryEntity buildMobileRechargeBeneficiaryEntity(String beneficiaryOid){
        return MobileRechargeBeneficiaryEntity.builder()
                        .mobileRechargeBeneficiaryOid(beneficiaryOid)
                        .beneficiaryAccountNo("01821308198")
                        .operator("ROBI")
                        .connectionType("Prepaid")
                        .beneficiaryType("mobile recharge")
                        .currency("BDT")
                        .build();

    }


}