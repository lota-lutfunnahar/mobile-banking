package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeSingleCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetToAccountInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetUserInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static reactor.core.publisher.Mono.when;

//@SpringBootTest
public class MobileRechargePreProcessServiceTest {

//    @Autowired
//    private MobileRechargePreProcessService mobileRechargePreProcessService;
//
//    @MockBean
//    MobileRechargeValidatorUseCase mobileRechargeValidatorUseCase;
//
//
//    private GetToAccountInfo toAccountInfo = mock(GetToAccountInfo.class);
//
//    private GetUserInfo getUserInfo =
//            mock(GetUserInfo.class);
//
//
//    @Test
//    void contextLoad(){
//        assertThat(mobileRechargePreProcessService).isNotNull();
//    }
//
//    @Test
//    void givenuserId__retutnUserByUserId() throws ExceptionHandlerUtil {
//        String userId = "01737711756";
//        IBUser ibUser = IBUser.builder()
//                .userId(userId)
//                .mobileNo("01737711756")
//                .status("Active")
//                .branchOid(null)
//                .build();
////        when(getUserInfo.findByUserId(any())).thenReturn(ibUser);
////        when(mobileRechargeValidatorUseCase.validateUser(ibUser)).thenReturn(ibUser);
//        IBUser user = mobileRechargePreProcessService.getUserByUserId(userId);
//        assertThat(user).isNotNull();
//    }
//
///*    @Test
//    void givenUserOid__returnCustomersByUserOid() throws ExceptionHandlerUtil{
//        String userOid = "9f4ebb1c-7f3a-4030-aa51-6727265dcfc7";
//        List<MobileRechargeCustomerEntity> ibConsumerEntityList = mobileRechargePreProcessService.getCustomersByUserOid(userOid);
//        assertThat(ibConsumerEntityList).isNotNull();
//    }*/
//
//  /*  @Test
//    void givenBeneficiaryAccount__returnValidBeneAccount() throws ExceptionHandlerUtil {
//        //given
//        String beneficiaryAccountNo = "01836814151";
//        //when
//        List<MobileRechargeTransBeneficiaryEntity> beneficiary = mobileRechargePreProcessService.getToMobileRechargeAccountInfo(beneficiaryAccountNo);
//        assertThat(beneficiary).isNotNull();
//        //then
//    }*/
}
