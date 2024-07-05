package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class LoadMobileRechargeBeneficiaryAdapterTest {
    @Autowired
    private LoadMobileRechargeBeneficiaryAdapter beneficiaryAdapter;

    @Test
    void contextLoad(){
        assertThat(beneficiaryAdapter).isNotNull();
    }

    @Test
    void givenBeneficiaryOid_returnsMobileRechargeBeneficiary(){
        //given
        String beneficiaryOid = "31e75e80-ab57-4e03-93d1-388ff43a41d3";
        //then
        Optional<MobileRechargeBeneficiary> beneficiary = beneficiaryAdapter.getBeneficiaryByOid(beneficiaryOid);
        assertThat(beneficiary.isPresent()).isTrue();
        assertThat(beneficiary.get()).isNotNull();
        assertThat(beneficiary.get().getBeneficiaryOid()).isEqualTo(beneficiaryOid);
    }

    @Test
    void givenBeneficiaryOidThatDoesNotExist_returnsEmptyOptional(){
        //given
        String beneficiaryOid = UUID.randomUUID().toString();
        //then
        Optional<MobileRechargeBeneficiary> beneficiary = beneficiaryAdapter.getBeneficiaryByOid(beneficiaryOid);
        assertThat(beneficiary.isPresent()).isFalse();
    }

    @Test
    void givenUserIdAndBeneficiaryAccountNo_returnsMobileRechargeBeneficiary(){
        //given
        String userId = "ba578981-a9fa-4fb1-a9a9-f846a8dc08e6";
        String beneficiaryAccountNo = "01821308198";
        //then
        Optional<MobileRechargeBeneficiary> beneficiary = beneficiaryAdapter.getBeneficiary(userId, beneficiaryAccountNo);
        assertThat(beneficiary.isPresent()).isTrue();
        assertThat(beneficiary.get()).isNotNull();
        assertThat(beneficiary.get().getBeneficiaryAccountNo()).isEqualTo(beneficiaryAccountNo);
    }

    @Test
    void givenUserIdAndBeneficiaryAccountNoThatDoesntExists_returnsEmptyOptional(){
        //given
        String userId = UUID.randomUUID().toString();
        String beneficiaryAccountNo = "01821308198";
        //then
        Optional<MobileRechargeBeneficiary> beneficiary = beneficiaryAdapter.getBeneficiary(userId, beneficiaryAccountNo);
        assertThat(beneficiary.isPresent()).isFalse();
    }

}