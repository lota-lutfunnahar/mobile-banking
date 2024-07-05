package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class UpdateMobileRechargeBeneficiaryAdapterTest {
    @Autowired
    UpdateMobileRechargeBeneficiaryAdapter updateAdapter;

    @Autowired
    AddMobileRechargeBeneficiaryAdapter addAdapter;

    @Test
    void contextLoad(){
        assertThat(updateAdapter).isNotNull();
    }

    @Test
    void givenMobileRechargeBeneficiary_returnsOptionalOfMobileRechargeBeneficiary(){
        //given
        Optional<MobileRechargeBeneficiary> newBeneficiary = addAdapter.saveBeneficiary(buildMobileRechargeBeneficiary());
        if (newBeneficiary.isPresent()) System.out.println(newBeneficiary.toString());
        MobileRechargeBeneficiary beneficiary = buildMobileRechargeBeneficiary();
        beneficiary.setShortName("Reza");
        beneficiary.setBeneficiaryAccountNo("018313081" + (int) Math.floor(Math.random() * 100));
        beneficiary.setBeneficiaryOid(newBeneficiary.get().getBeneficiaryOid());
        //then
        Optional<MobileRechargeBeneficiary> updatedBeneficiary = updateAdapter.update(beneficiary);
        assertThat(updatedBeneficiary.isPresent()).isTrue();
        assertThat(updatedBeneficiary.get().getBeneficiaryAccountNo()).isEqualTo(beneficiary.getBeneficiaryAccountNo());

    }

    @Test
    void givenMobileRechargeBeneficiary_returnsEmptyOptionalIfNotFound(){
        //given
        MobileRechargeBeneficiary beneficiary = buildMobileRechargeBeneficiary();
        beneficiary.setBeneficiaryOid(UUID.randomUUID().toString());
        //then
        Optional<MobileRechargeBeneficiary> updatedBeneficiary = updateAdapter.update(beneficiary);
        assertThat(updatedBeneficiary.isPresent()).isFalse();
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