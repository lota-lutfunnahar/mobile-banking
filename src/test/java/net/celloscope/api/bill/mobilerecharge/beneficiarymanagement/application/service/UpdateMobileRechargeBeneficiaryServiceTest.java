package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.AddMobileRechargeBeneficiaryAdapter;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class UpdateMobileRechargeBeneficiaryServiceTest {
    @Autowired
    UpdateMobileRechargeBeneficiaryService updateService;

    @Autowired
    AddMobileRechargeBeneficiaryAdapter addAdapter;

    @Test
    void contextLoad(){
        assertThat(updateService).isNotNull();
    }

    @Test
    void givenUpdateRequest_returnsMobileRechargeBeneficiaryUpdateResponse() throws Exception{
        //given
        Optional<MobileRechargeBeneficiary> savedBeneficiary = addAdapter.saveBeneficiary(buildMobileRechargeBeneficiary());
        if (savedBeneficiary.isPresent()) System.out.println(savedBeneficiary.toString());
        MobileRechargeBeneficiaryUpdateRequest updateRequest = buildUpdateRequest(savedBeneficiary.get().getBeneficiaryOid());
        //then
        MobileRechargeBeneficiaryUpdateResponse updatedBeneficiary = updateService.updateBeneficiary(updateRequest);

        assertThat(updatedBeneficiary.getBeneficiaryOid()).isEqualTo(savedBeneficiary.get().getBeneficiaryOid());
        assertThat(updatedBeneficiary.getUserMessage()).isEqualTo(Messages.BENEFICIARY_UPDATED);
    }

    @Test
    void givenUpdateRequestOfNonExistingBeneficiaryOid_throwsExceptionOfNotFound(){
        //given
        MobileRechargeBeneficiaryUpdateRequest request = buildUpdateRequest(null);
        //then
        assertThatThrownBy(() -> updateService.updateBeneficiary(request)).isInstanceOf(ExceptionHandlerUtil.class)
                .hasMessage(Messages.BENEFICIARY_ACCOUNT_NOT_FOUND);
    }

   private MobileRechargeBeneficiaryUpdateRequest buildUpdateRequest(String beneficiaryOid){
        return MobileRechargeBeneficiaryUpdateRequest.builder()
                .beneficiaryOid(beneficiaryOid == null ? UUID.randomUUID().toString() : beneficiaryOid)
                .beneficiaryAccountNo("018313081" + (int) Math.floor(Math.random() * 100))
                .operator("Robi")
                .connectionType("prepaid")
                .shortName("naim_" + (int) Math.floor(Math.random() * 10))
                .description("update description")
                .build();
    }

    private MobileRechargeBeneficiary buildMobileRechargeBeneficiary(){
        return MobileRechargeBeneficiary.builder()
                .beneficiaryAccountNo("018213082" + (int) Math.floor(Math.random() * 100))
                .operator("ROBI")
                .connectionType("PREPAID")
                .shortName("Naim")
                .currency("BDT")
                .description("some description")
                .userId("4e973146-4638-4192-a438-62f4dce8b881")
                .build();
    }

}