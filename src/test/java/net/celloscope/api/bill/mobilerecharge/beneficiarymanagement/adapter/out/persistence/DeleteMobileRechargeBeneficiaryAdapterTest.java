package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;


import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class DeleteMobileRechargeBeneficiaryAdapterTest {
    @Autowired
    DeleteMobileRechargeBeneficiaryAdapter deleteAdapter;

    @Autowired
    AddMobileRechargeBeneficiaryAdapter addAdapter;

    @Test
    void contextLoad(){
        assertThat(deleteAdapter).isNotNull();
    }

    @Test
    void givenBeneficiaryOid_deletesBeneficiary_returnsDeletedBeneficiary(){
        //given
        Optional<MobileRechargeBeneficiary> beneficiary = addAdapter.saveBeneficiary(buildMobileRechargeBeneficiary());
        if (beneficiary.isPresent()) System.out.println(beneficiary.toString());
        //then
        Optional<MobileRechargeBeneficiary> deletedEntity = deleteAdapter.delete(beneficiary.get().getBeneficiaryOid());
        assertThat(deletedEntity.isPresent()).isTrue();
        assertThat(deletedEntity.get().getBeneficiaryOid()).isEqualTo(beneficiary.get().getBeneficiaryOid());
        assertThat(deletedEntity.get().getBeneficiaryAccountNo()).isNotNull();
        assertThat(deletedEntity.get().getBeneficiaryType()).isEqualTo("mobile recharge");
    }

    @Test
    void givenNonExistingBeneficiaryOid_returnsEmptyOptional(){
        //given
        String beneficiaryOid = UUID.randomUUID().toString();
        //then
        Optional<MobileRechargeBeneficiary> deletedBeneficiary = deleteAdapter.delete(beneficiaryOid);
        assertThat(deletedBeneficiary.isPresent()).isFalse();
    }

    private MobileRechargeBeneficiary buildMobileRechargeBeneficiary(){
        return MobileRechargeBeneficiary.builder()
                .beneficiaryAccountNo("01821308298")
                .operator("ROBI")
                .connectionType("PREPAID")
                .shortName("Naim")
                .currency("BDT")
                .description("some description")
                .userId("4e973146-4638-4192-a438-62f4dce8b881")
                .build();
    }

}