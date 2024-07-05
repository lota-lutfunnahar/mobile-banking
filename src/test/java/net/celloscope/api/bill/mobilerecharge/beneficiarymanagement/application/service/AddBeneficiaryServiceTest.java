package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryRepository;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import net.celloscope.api.core.util.Messages;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class AddBeneficiaryServiceTest {

    @Autowired
    private AddBeneficiaryService addBeneficiaryService;

    @MockBean
    private MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository;

    @MockBean
    private LoadIBUser loadIBUser;

    @Test
    void contextLoad(){
        assertThat(addBeneficiaryService).isNotNull();
    }

    @Test
    void givenAddBeneficiaryRequest_returnsAddBeneficiaryResponse() throws Exception{
        //given
        String userId = UUID.randomUUID().toString();
        AddBeneficiaryRequest request = buildAddBeneficiaryRequest();
        //when
        when(loadIBUser.getIBUser(any())).thenReturn(buildIbUserDomain());
        when(mobileRechargeBeneficiaryRepository.findByUserIdAndBeneficiaryAccountNo(any(), any())).thenReturn(Optional.empty());
        when(mobileRechargeBeneficiaryRepository.save(any())).thenReturn(buildMobileRechargeBeneficiaryEntity());
        //then
        AddBeneficiaryResponse response = addBeneficiaryService.addBeneficiary(request, userId);
        assertThat(response).isNotNull();
        assertThat(response.getUserMessage()).isEqualTo(Messages.BENEFICIARY_ADDED);
    }

    @Test
    void givenExistingAddBeneficiaryRequest_throwsBeneficiaryExistsException(){
        //given
        String userId = UUID.randomUUID().toString();
        AddBeneficiaryRequest request = buildAddBeneficiaryRequest();
        //when
        when(loadIBUser.getIBUser(any())).thenReturn(buildIbUserDomain());
        when(mobileRechargeBeneficiaryRepository.findByUserIdAndBeneficiaryAccountNo(any(), any())).thenReturn(Optional.of(buildMobileRechargeBeneficiaryEntity()));
        //then
        assertThatThrownBy(() -> addBeneficiaryService.addBeneficiary(request, userId));
    }


    private AddBeneficiaryRequest buildAddBeneficiaryRequest(){
        return AddBeneficiaryRequest.builder()
                .beneficiaryAccountNo("01821308198")
                .shortName("Naim")
                .operator("ROBI")
                .connectionType("PREPAID")
                .description("internet banking")
                .currency("BDT")
                .build();
    }

    private MobileRechargeBeneficiaryEntity buildMobileRechargeBeneficiaryEntity(){
        return MobileRechargeBeneficiaryEntity.builder()
                .mobileRechargeBeneficiaryOid(UUID.randomUUID().toString())
                .beneficiaryType("mobile recharge")
                .beneficiaryAccountNo("01821308198")
                .shortName("Naim")
                .operator("ROBI")
                .connectionType("PREPAID")
                .description("some description")
                .currency("BDT")
                .build();
    }

    private Optional<IBUserDomain> buildIbUserDomain(){
        return Optional.of(
                IBUserDomain.builder()
                        .ibUserOid(UUID.randomUUID().toString())
                        .build()
        );
    }


}