package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeCallBackUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.*;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@Service
public class MobileRechargeCallBackService implements MobileRechargeCallBackUseCase {
    private final TransactionInfo transactionInfo;
    private final GetUserInfo getUserInfo;
    private final MobileRechargeValidatorUseCase mobileRechargeValidatorUseCase;
    private final GetToAccountInfo getAccountInfo;
    private final GetProductInfo productInfo;
    private final GetBalanceInfo balanceInfo;
    private final SavePreProcessTransaction processTransaction;
    private final CoreCorrectFtRequest coreCorrectFtRequest;
    private final MobileRechargeProcessService processService;

    @Value("${robi.settlement.bank.account.number}")
    private String robiSettlementBankAccountNo;

    public MobileRechargeCallBackService(TransactionInfo transactionInfo, GetUserInfo getUserInfo,
                                         MobileRechargeValidatorUseCase mobileRechargeValidatorUseCase,
                                         GetToAccountInfo getAccountInfo, GetProductInfo productInfo, GetBalanceInfo balanceInfo,
                                         SavePreProcessTransaction processTransaction, CoreCorrectFtRequest coreCorrectFtRequest,
                                         MobileRechargeProcessService processService) {
        this.transactionInfo = transactionInfo;
        this.getUserInfo = getUserInfo;
        this.mobileRechargeValidatorUseCase = mobileRechargeValidatorUseCase;
        this.getAccountInfo = getAccountInfo;
        this.productInfo = productInfo;
        this.balanceInfo = balanceInfo;
        this.processTransaction = processTransaction;
        this.coreCorrectFtRequest = coreCorrectFtRequest;
        this.processService = processService;
    }

    @Override
    public ResponseEntity<RobiTransactionCallBackResponse> getCallBack(RobiTransactionCallBackRequest request) throws ExceptionHandlerUtil, JSONException, ParseException {

        if(request.getStatus().equalsIgnoreCase(FAILED) && request.getErrorCode() != null) {
            MobileRechargeTransaction rechargeTransaction = transactionInfo.findByTransactionOid(request.getRequestId());
            IBUser user = getUserByUserId(rechargeTransaction.getCreatedBy());
            List<MobileRechargeCustomerEntity> customers = getCustomersByUserOid(user.getIbUserOid());
            Account fromAccount = getAccountInfo(rechargeTransaction.getDebitedAccountOid());
            reverseRobiTransaction(rechargeTransaction, request, customers, fromAccount, user, rechargeTransaction.getTraceId());
        }  else if (request.getStatus().equalsIgnoreCase(OK)){
            //TODO call firebase notification
            log.info("Received response of Robi interface api success for deposit : {}", request.getStatus());
        } else if (request.getStatus().equalsIgnoreCase(WAITING_FOR_RECONCILE) && request.getErrorCode() != null ){
            log.info("Received response of Robi interface api  pending for online e Money issue code : {} message : {} status : {} ",
                    request.getErrorCode(), request.getErrorMessage(), request.getStatus());
        }
        RobiTransactionCallBackResponse details = RobiTransactionCallBackResponse.builder()
                .userMessage("Call back api receive successfully")
                .build();

        return new ResponseEntity<RobiTransactionCallBackResponse>(details, HttpStatus.OK);
    }

    public IBUser getUserByUserId(String userId) throws ExceptionHandlerUtil {
        IBUser ibUser = getUserInfo.findByUserId(userId);
        mobileRechargeValidatorUseCase.validateUser(ibUser);
        return ibUser;
    }

    public Account getAccountInfo(String accountOid) throws ExceptionHandlerUtil {
        Account account = getAccountInfo.findAccountInfo(accountOid);
        if (account == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND);
        }

        String productType = productInfo.findByProductOid(account.getProductOid()).getProductType();
        account.setProductType(productType);
        return account;
    }

    public void reverseRobiTransaction(MobileRechargeTransaction transactionDetails, RobiTransactionCallBackRequest request,
                                       List<MobileRechargeCustomerEntity> customer, Account fromAccount,
                                       IBUser userEntity, String traceId) throws ExceptionHandlerUtil, ParseException, JSONException {

        log.info("Request for reverse ft for fail robi request {}",  transactionDetails.getTransactionOid());
        BigDecimal workingBalance = balanceInfo.getBalance(robiSettlementBankAccountNo);
        MobileRechargeTransaction rechargeTransaction = saveReverseTransaction(request, customer, fromAccount, userEntity, traceId);
//        bkashTransactionValidator.isBalanceGraterThenTotal(workingBalance, request.getAmount()
//                .add(rechargeTransaction.getChargeAmount().add(rechargeTransaction.getVatAmount())));
        CBSFtResponse cbsResponseReverseObject = coreCorrectFtRequest.sendCoreCorrectFtRequest(rechargeTransaction, "Yes",
                robiSettlementBankAccountNo.substring(0, 4), robiSettlementBankAccountNo,
                fromAccount.getBranchOid(), fromAccount.getBankAccountNo(),
                request.getAmount(), traceId);

        Date cbsTransactionReverseDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").parse(cbsResponseReverseObject.getCbsTransactionDate());
        processService.updateCbsReferenceNoAndCbsTraceNo(cbsResponseReverseObject.getReferenceNo(), cbsResponseReverseObject.getCbsTraceNo(), cbsTransactionReverseDate,
                cbsResponseReverseObject.getTransactionRequestDate(), rechargeTransaction);

    }
    private List<MobileRechargeCustomerEntity> getCustomersByUserOid(String ibUserOid) throws ExceptionHandlerUtil {
        MobileRechargeIbConsumerEntity ibConsumer = getUserInfo.findByIbUserOid(ibUserOid);
        if (ibConsumer == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, CONSUMER_NOT_FOUND);
        }
        if (ibConsumer.getCustomers() == null || !ibConsumer.getCustomers().iterator().hasNext()) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, CUSTOMER_NOT_FOUND);
        }
        List<MobileRechargeCustomerEntity> customers = ibConsumer.getCustomers().stream()
                .filter(cus -> cus.getBankOid().equals(BANK_OID_35) && cus.getStatus().equalsIgnoreCase(OPERATIVE))
                .collect(Collectors.toList());

        if(customers.size() < 1)
        {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, ACTIVE_CUSTOMER_NOT_FOUND);
        }
        return customers;
    }

    public MobileRechargeTransaction saveReverseTransaction(RobiTransactionCallBackRequest request, List<MobileRechargeCustomerEntity> customer, Account fromAccount,
                                                    IBUser userEntity, String traceId) throws ExceptionHandlerUtil {

        TransactionIntermediateStatus intermediateStatusDto = TransactionIntermediateStatus.builder()
                .isPreValidatedSuccessful(false)
                .isPreValidatedFailed(false)
                .isTransactionHashMatched(false)
                .isCbsFTSuccessful(false)
                .isCbsFTFailed(false)
                .isMfsReverseFT(false)
                .isMfsDepositSuccessful(false)
                .isMfsDepositFailed(false)
                .isMfsDepositQueued(false)
                .isRobiRechargeFailed(false)
                .isRobiRechargeSuccessful(false)
                .isRobiRechargeQueued(false)
                .isRobiReverseFT(true)
                .build();

        MobileRechargeTransaction rechargeTransaction = MobileRechargeTransaction.builder()
                .transType(INTRA)
                .transAmount(request.getAmount())
                .chargeAmount(BigDecimal.valueOf(0.00))
                .vatAmount(BigDecimal.valueOf(0.00))
                .transStatus("PreProcess")
                .bankOid(BANK_OID_35)
                .ibUserOid(userEntity.getIbUserOid())
                .requestId(request.getRequestId())
                .remarks(ROBI_FT_REVERSE_REMARKS)
                .traceId(traceId)
                .debitedAccount(robiSettlementBankAccountNo)
                .debitedAccountTitle("robi settlement ac")
                .debitedAccountBranchOid(robiSettlementBankAccountNo.substring(0, 4))
                .creditedAccount(fromAccount.getBankAccountNo())
                .creditedAccountTitle(fromAccount.getAccountTitle())
                .creditedAccountCustomerOid(customer.get(0).getCustomerOid())
                .creditedAccountCbsCustomerId(customer.get(0).getBankCustomerId())
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(userEntity.getUserId())
                .intermediateStatus(intermediateStatusDto)
                .currency(request.getCurrency())
                .build();
        rechargeTransaction = processTransaction.saveTransaction(rechargeTransaction);;
        log.info("Robi transaction pre-process data saved : {}", rechargeTransaction);
        return rechargeTransaction;
    }
}
