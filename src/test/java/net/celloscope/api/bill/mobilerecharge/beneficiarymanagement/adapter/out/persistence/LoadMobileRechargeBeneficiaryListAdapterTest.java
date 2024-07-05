package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LoadMobileRechargeBeneficiaryListAdapterTest {

    @Autowired
    private LoadBeneficiaryListAdapter loadBeneficiaryListAdapter;

    @MockBean
    private MobileRechargeBeneficiaryRepository beneficiaryRepository;

    
    @Test
    void contextLoad(){
        assertThat(loadBeneficiaryListAdapter).isNotNull();
    }

    @Test
    void givenUserOid_returnsListOfMobileRechargeBeneficiary(){
        //given
        String userId = UUID.randomUUID().toString();
        //when
        when(beneficiaryRepository.findByUserId(any())).thenReturn(buildMobileRechargeBeneficiaryEntity());
        //then
        List<MobileRechargeBeneficiary> beneficiaries = loadBeneficiaryListAdapter.getBeneficiaryList(userId);
        assertThat(beneficiaries).isNotNull();
        assertThat(beneficiaries.size()).isEqualTo(5);
    }

    @Test
    void givenUserOid_returnsNullIfNoBeneficiaryFound(){
        //given
        String userId = UUID.randomUUID().toString();
        //when
        when(beneficiaryRepository.findByUserId(any())).thenReturn(null);
        //then
        List<MobileRechargeBeneficiary> beneficiaries = loadBeneficiaryListAdapter.getBeneficiaryList(userId);
        assertThat(beneficiaries).isNull();
    }

    private List<MobileRechargeBeneficiaryEntity> buildMobileRechargeBeneficiaryEntity(){
        List<MobileRechargeBeneficiaryEntity> entities = new ArrayList<>();
        List<String> names = Arrays.asList("Naim", "Tanvir", "Taslim", "Lota", "Nova");

        names.forEach(name -> {
            MobileRechargeBeneficiaryEntity entity = MobileRechargeBeneficiaryEntity.builder()
                    .shortName(name)
                    .beneficiaryAccountNo("01821308198")
                    .accountTitle(name)
                    .beneficiaryType("mobile recharge")
                    .build();
            entities.add(entity);
        });

        return entities;
    }

}