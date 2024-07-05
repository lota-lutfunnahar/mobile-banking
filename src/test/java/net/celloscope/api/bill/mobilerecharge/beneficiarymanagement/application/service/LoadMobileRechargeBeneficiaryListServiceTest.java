package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import static org.assertj.core.api.Assertions.*;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadBeneficiaryList;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
public class LoadMobileRechargeBeneficiaryListServiceTest {

    @Autowired
    private LoadBeneficiaryListService loadBeneficiaryListService;

    @MockBean
    private LoadBeneficiaryList loadBeneficiaryList;

    @MockBean
    private LoadIBUser loadIBUser;

    @Test
    void contextLoad(){
        assertThat(loadBeneficiaryListService).isNotNull();
    }

    @Test
    void givenABeneficiaryListRequest_returnsAListOfBeneficiary() throws Exception{
        //given
        BeneficiaryListRequest request = buildBeneficiaryListRequest();
        //when
        when(loadIBUser.getIBUser(any())).thenReturn(buildIbUserDomain());
        when(loadBeneficiaryList.getBeneficiaryList(any())).thenReturn(buildBeneficiaryList());
        //then
        BeneficiaryListResponse beneficiaries = loadBeneficiaryListService.getBeneficiaries(request);
        assertThat(beneficiaries).isNotNull();
    }

    @Test
    void givenABeneficiaryListRequest_throwsExceptionWhenBeneficiaryNotFound() throws Exception{
        //given
        BeneficiaryListRequest request = buildBeneficiaryListRequest();
        //when
        when(loadIBUser.getIBUser(any())).thenReturn(buildIbUserDomain());
        when(loadBeneficiaryList.getBeneficiaryList(any())).thenReturn(null);
        //then
        assertThatThrownBy(() -> loadBeneficiaryListService.getBeneficiaries(request))
                .isInstanceOf(ExceptionHandlerUtil.class)
                .hasMessage(Messages.BENEFICIARY_LIST_NOT_FOUND);
    }

    private BeneficiaryListRequest buildBeneficiaryListRequest(){
        return BeneficiaryListRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .requestTime(new Date().toString())
                .requestTime("30")
                .build();
    }

    private List<MobileRechargeBeneficiary> buildBeneficiaryList(){
        List<MobileRechargeBeneficiary> beneficiaries = new ArrayList<>();
        List<String> names = Arrays.asList("Lota", "Tanvir", "Nova", "Mou");
        MobileRechargeBeneficiary mobileRechargeBeneficiary = MobileRechargeBeneficiary.builder()
                .beneficiaryOid(UUID.randomUUID().toString())
                .shortName("Naim")
                .operator("ROBI")
                .connectionType("PREPAID")
                .beneficiaryAccountNo("01816390108")
                .currency("BDT")
                .build();

        names.forEach(name -> {
            mobileRechargeBeneficiary.setShortName(name);
            beneficiaries.add(mobileRechargeBeneficiary);
        });

        return beneficiaries;
    }

    private Optional<IBUserDomain> buildIbUserDomain(){
        return Optional.of(
                IBUserDomain.builder()
                        .ibUserOid(UUID.randomUUID().toString())
                        .build()
        );
    }

}
