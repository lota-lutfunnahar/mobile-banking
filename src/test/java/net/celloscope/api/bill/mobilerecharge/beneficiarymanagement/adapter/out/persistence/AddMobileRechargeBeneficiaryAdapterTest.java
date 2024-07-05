package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class AddMobileRechargeBeneficiaryAdapterTest {

    @Autowired
    AddMobileRechargeBeneficiaryAdapter adapter;

    @Test
    void contextLoad(){
        assertThat(adapter).isNotNull();
    }

    @Test
    void givenMobileRechargeBeneficiary_savesTheBeneficiary(){
        //given
        MobileRechargeBeneficiary beneficiary = buildMobileRechargeBeneficiary();
        //then
        Optional<MobileRechargeBeneficiary> savedEntity = adapter.saveBeneficiary(beneficiary);
        assertThat(savedEntity.isPresent()).isTrue();
        assertThat(savedEntity.get().getBeneficiaryAccountNo()).isEqualTo(beneficiary.getBeneficiaryAccountNo());

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