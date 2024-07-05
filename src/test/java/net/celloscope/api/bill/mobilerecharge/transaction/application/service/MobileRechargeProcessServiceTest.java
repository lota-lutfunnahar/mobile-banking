package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import net.celloscope.api.account.entity.AccountEntity;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiMobileRechargeResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.consumer.entity.IbConsumerEntity;
import net.celloscope.api.core.service.AbsClientService;
import net.celloscope.api.core.service.MobileRechargeClientService;
import net.celloscope.api.core.service.RobiClientService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.customerInfo.entity.CustomerEntity;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import net.celloscope.api.transaction.entity.TransactionEntity;
import net.celloscope.api.user.entity.IbUserEntity;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import static net.celloscope.api.core.util.Messages.*;
import static net.celloscope.api.core.util.Messages.I_BANKING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MobileRechargeProcessServiceTest {

    @Autowired
    MobileRechargeProcessService processService;

    private MobileRechargeClientService rechargeClientService = mock(MobileRechargeClientService.class) ;

    @Test
    void contextLoad(){
        assertThat(processService).isNotNull();
    }

    /*@Test
    void givenRequest__returnSuccessResponse() throws Exception{
        //given
        MobileRechargeProcessRequest request = new MobileRechargeProcessRequest().builder()
                .requestId("35447cb1-3d51-4ebb-a274-4b57acfbac33")
                .amount(String.valueOf(BigDecimal.valueOf(200).setScale(2)))
                .vatAmount(String.valueOf(BigDecimal.valueOf(0).setScale(2)))
                .chargeAmount(String.valueOf(BigDecimal.valueOf(0).setScale(2)))
                .connectionType("PREPAID")
                .currency("BDT")
                .accountId("187addd3-f658-488c-ae38-af4bd9479bfa")
                .grandTotal(String.valueOf(BigDecimal.valueOf(200).setScale(2)))
                .operator("ROBI")
                .transactionType("mobile recharge")
                .otp("7236")
                .refId("7eb9fc9f-68b6-4cdc-aa52-9958f57643ea")
                .toAccount("01722605387")
                .traceId("74dea1c03919e8c7")
                .userId("01737711756")
                .build();

        ResponseEntity<Map<String, Object>>  reponse =  processService.transactionProcess(request, "30");
        assertThat(reponse).isNotNull();
    }*/

    /*@Test
    void givenAccountOid__returnAccount() throws ExceptionHandlerUtil {
        //given
        String accountOid = "ae256741-d412-4f55-aa99-ebf45248c163";
        //then
        Account account = processService.getAccountInfo(accountOid);
        Assertions.assertThat(account).isNotNull();
    }*/

    /*@Test
    void givenRequestInfoForMobileRecharge_returnSuccessResponse() throws ExceptionHandlerUtil {

        RobiClientService.RobiProcessRequest robiProcessRequest = RobiClientService.RobiProcessRequest.builder()
                .originatorConversationId("3q2yWFjBUY") // TODO updated transaction for originatorConversationId
                .requestId("3q2yWFjBUY")
                .mobileNo("01836814151")
                .amount(10)
                .currency("BDT")
                .remarks("Robi recharge test")
                .connectionType("PREPAID")
                .operator("ROBI")
                .userId("01737711756")
                .build();


        ResponseEntity<RobiMobileRechargeResponse> robiResponse = rechargeClientService.getRobiFtResponse(robiProcessRequest);
        System.out.println(robiResponse);
        Assertions.assertThat(robiResponse).isNotNull();

    }*/

   /* @Test
    void givenRequestInfoForMobileRecharge_returnPendingResponse() throws ExceptionHandlerUtil, JSONException, ParseException {

        when(rechargeClientService.getRobiFtResponse(any())).thenReturn(buildRobiResponse());
        RobiMobileRechargeResponse robiResponse = processService.sendRobiFtRequest(transactionEntity, "", request,
                null, null, ACCOUNT_1, USER_ENTITY, "traceId");
        Assertions.assertThat(robiResponse).isNotNull();

    }*/

    private ResponseEntity<RobiMobileRechargeResponse> buildRobiResponse(){
        RobiMobileRechargeResponse robiMobileRechargeResponse =  RobiMobileRechargeResponse.builder()
                .status("Pending")
                .errorCode("423")
                .transactionDate(new Date())
                .errorMessage("failed")
                .build();
        return new ResponseEntity<RobiMobileRechargeResponse>(robiMobileRechargeResponse, HttpStatus.LOCKED);
    }

    private static final IBUser USER_ENTITY = IBUser.builder()
            .ibUserOid("IB_USER_0ID")
            .userId("USER_ID")
            .build();

    private static final Account ACCOUNT_1 = Account.builder()
            .accountOid("dsfdggdr44545-dfgfdgwe-dffghru-589jmj")
            .bankAccountNo("0200008617565")
            .accountTitle("Kowsar")
            .productOid("6001")
            .currency("BDT")
            .cbsAccountStatus(STATUS_OPERATIVE)
            .bankOid("35")
            .branchOid("BD0011085")
            .currentVersion("1")
            .status(STATUS_OPERATIVE)
            .createdOn(Timestamp.valueOf(LocalDateTime.now()))
            .build();

//    private static final List<MobileRechargeCustomerEntity> CUSTOMER_1 = (List<MobileRechargeCustomerEntity>) CustomerEntity.builder()
//            .fullName("Kowsar")
//            .mobileNo("01818249357")
//            .dateOfBirth(Timestamp.valueOf(LocalDateTime.now()))
//            .bankOid("35")
//            .status(STATUS_OPERATIVE)
//            .createdOn(Timestamp.valueOf(LocalDateTime.now()))
//            .build();


    MobileRechargeProcessRequest request = MobileRechargeProcessRequest.builder()
            .operator("ROBI")
            .connectionType("PREPAID")
            .userId("01818249357")
            .toAccount("01818249357")
            .build();

    MobileRechargeTransaction transactionEntity = MobileRechargeTransaction.builder()
            .transactionOid("1223343324344234")
            .transType("Kowsar")
            .transAmount(new BigDecimal(200))
            .chargeAmount(new BigDecimal(0.0))
            .vatAmount(new BigDecimal(0.0))
            .transStatus("OtpSent")
            .bankOid("35")
            .requestId("1234")
            .traceId("1234XYDGA")
            .debitedAccountOid("sjhjjh")
            .debitedAccount("0200008627061")
            .debitedAccountCustomerOid("fdfgdffdg-4434fgdfg-wgrref")
            .debitedAccountCbsCustomerId("ae466720-29d8-40bc-91dc-d14832db7828")
            .debitedAccountBranchOid("BD0014006")
            .creditedAccountOid("dsfdsfd-wr35y-gfddfg")
            .creditedAccount("0200008627060")
            .creditedAccountCustomerOid("fdfgdffdg-4434fgdfg-wgrref")
            .creditedAccountCbsCustomerId("fdfgdffdg-4434fgdfg-wgrref")
            .creditedAccountBranchOid("BD0014006")
            .currency("BDT")
            .offsetOtp("892371")
            .createdBy("Kowsar")
            .createdOn(Timestamp.valueOf(LocalDateTime.now()))
            .remarks("Transfer")
            .build();
}
