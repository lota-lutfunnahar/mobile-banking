package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargePreProcessUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.*;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.*;
import net.celloscope.api.core.client.ChargeModelClient;
import net.celloscope.api.core.client.PinServerClient;
import net.celloscope.api.core.common.Amount;
import net.celloscope.api.core.service.AbsClientService;
import net.celloscope.api.core.service.UtilityService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Utility;
import net.celloscope.api.metaProperty.entity.MetaProperty;
import net.celloscope.api.metaProperty.service.MetaPropertyHelperService;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileRechargePreProcessService implements MobileRechargePreProcessUseCase {

    private final GetUserInfo getUserInfo;
    private final GetProductInfo productInfo;
    private final GetToAccountInfo toAccountInfo;
    private final GetToAccountInfo getAccountInfo;
    private final GetBalanceInfo balanceInfo;
    private final SavePreProcessTransaction processTransaction;
    private final UpdateTransactionProcess updateTransactionProcess;
    private final MobileRechargeValidatorUseCase mobileRechargeValidatorUseCase;
    private final Utility utility;
    private final ChargeModelClient chargeModelClient;
    private final UtilityService utilityService;
    private final PinServerClient pinServerClient;
    private final MetaPropertyHelperService metaPropertyService;


    @Value("${robi.settlement.bank.account.number}")
    private String robiSettlementBankAccountNo;

    @Value("${metaData.otp-validity.code}")
    private String otpValidityMetaCode;

    @Value("${metaData.otp-validity.name}")
    private String otpValidityMetaName;

    private static List<MetaProperty> otpValidityMetaData = new ArrayList<>();

    @Override
    public ResponseEntity<MobileRecargePreProcessResponse> transactionPreProcess(MobileRecargePreProcessRequest request) throws ExceptionHandlerUtil {

        IBUser user = getUserByUserId(request.getUserId());
        List<MobileRechargeCustomerEntity> customers = getCustomersByUserOid(user.getIbUserOid());
        Account debitAccount = getAccountInfo(request.getAccountId());
        List<MobileRechargeTransBeneficiaryEntity> creditAccount = getToMobileRechargeAccountInfo(request.getToAccount());
        MobileRechargeCustomerEntity customer = customers.get(0);

        if (!checkRequestAmountValidation(Integer.valueOf(request.getAmount()))) {
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, "Invalid Request Amount.");
        }

        mobileRechargeValidatorUseCase.validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(debitAccount);

        BigDecimal workingBalance = balanceInfo.getBalance(debitAccount.getBankAccountNo()); // todo no connectivity

        mobileRechargeValidatorUseCase.isDebitCreditAccountDifferent(debitAccount, request.getToAccount());
        mobileRechargeValidatorUseCase.isCreditAccountFromSameCustomer(customers, request.getToAccount());
        mobileRechargeValidatorUseCase.isDebitAccountActive(debitAccount);
        mobileRechargeValidatorUseCase.isDebitAccountTransactional(debitAccount);
        mobileRechargeValidatorUseCase.checkTransactionProfile(debitAccount.getBankAccountNo(), debitAccount.getProductOid(), new BigDecimal(request.getAmount()));

        MobileRechargeTransaction transaction = saveTransaction(request, customer, debitAccount, user, robiSettlementBankAccountNo);
        mobileRechargeValidatorUseCase.isBalanceGraterThenTotal(workingBalance, new BigDecimal(request.getAmount())
                .add(transaction.getChargeAmount().add(transaction.getVatAmount()))); // todo

        utilityService.saveHashValue(transaction.getTransactionOid(), request.getTraceId(), debitAccount.getBankAccountNo(), request.getToAccount(),
                request.getAmount(), user.getMobileNo());

        sendOtp(request, transaction, user);
        MobileRecargePreProcessResponse transactionResponse = buildResponse(transaction, debitAccount, request.getToAccount(), request.getConnectionType(), request.getOperator(), request.getRemarks());
        ResponseEntity<MobileRecargePreProcessResponse> response = new ResponseEntity<>(transactionResponse, HttpStatus.OK);

        return response;
    }

    private boolean checkRequestAmountValidation(Integer amount) {
        return amount >= Integer.parseInt(Amount.MINIMUM.getValue())
                && amount < Integer.parseInt(Amount.MAXIMUM.getValue());
    }

    public MobileRechargeTransaction saveTransaction(MobileRecargePreProcessRequest request, MobileRechargeCustomerEntity customer,
                                                     Account debitAccount, IBUser userEntity,
                                             String robiSettlementBankAccountNo) throws ExceptionHandlerUtil {


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

        MobileRechargeTransaction transaction = MobileRechargeTransaction.builder()
                .transType(ROBI)
                .transAmount(new BigDecimal(request.getAmount()).setScale(2))
                .vatAmount(BigDecimal.valueOf(0).setScale(2))
                .chargeAmount(BigDecimal.valueOf(0).setScale(2))
                .transStatus("PreProcess")
                .ibUserOid(userEntity.getIbUserOid())
                .bankOid(BANK_OID_35)
                .requestId(utility.getRandomAlphaNumericString(10))
                .remarks(request.getRemarks() == null || request.getRemarks() == "" ? "BKBApp " :  "BKBApp "+request.getRemarks())
                .traceId(request.getTraceId())
                .debitedAccountOid(debitAccount.getAccountOid())
                .debitedAccount(debitAccount.getBankAccountNo())
                .debitedAccountTitle(debitAccount.getAccountTitle())
                .debitedAccountCustomerOid(customer.getCustomerOid())
                .debitedAccountCbsCustomerId(customer.getBankCustomerId())
                .debitedAccountBranchOid(debitAccount.getBranchOid())
                .creditedAccount(robiSettlementBankAccountNo)
                .creditedAccountTitle("Robi settlement ac")
                .creditedAccountBranchOid(robiSettlementBankAccountNo.substring(0, 4))
                .customerAccount(request.getToAccount())
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .intermediateStatus(intermediateStatusDto)
                .createdBy(userEntity.getUserId())
                .currency(request.getCurrency())
                .build();
        //todo
        this.calculateCharge(debitAccount.getBankAccountNo(), debitAccount.getProductOid(), debitAccount.getBranchOid(), new BigDecimal(request.getAmount()), transaction);
        transaction = processTransaction.saveTransaction(transaction);
        log.info("Mobile recharge transaction pre-process data saved : {}", transaction);
        return transaction;
    }

    public MobileRecargePreProcessResponse buildResponse(MobileRechargeTransaction transaction, Account debitAccount,String creditRobiAccount, String connectionType, String operator, String remarks) throws ExceptionHandlerUtil {
        otpValidityMetaData = metaPropertyService.getValueJson(otpValidityMetaCode, otpValidityMetaName);
        String timeToLiveString = otpValidityMetaData.get(0).getValue().get(0);

        MobileRecargePreProcessResponse transactionResponse = MobileRecargePreProcessResponse.builder()
                .userMessage(TRANSACTION_REQUEST_SAVED)
                .transactionRequestRefId(transaction.getTransactionOid())
                .otpValidTimeInSec(timeToLiveString)
                .fromAccountId(debitAccount.getAccountOid())
                .fromAccount(debitAccount.getBankAccountNo())
                .fromProductName(debitAccount.getProductName())
                .toAccount(creditRobiAccount)
                .amount(transaction.getTransAmount().setScale(0).toString())
                .vatAmount(transaction.getVatAmount().setScale(0).toString())
                .chargeAmount(transaction.getChargeAmount().setScale(0).toString())
                .grandTotal(String.valueOf(transaction.getTransAmount().setScale(0).add(transaction.getChargeAmount().setScale(0)).add(transaction.getVatAmount().setScale(0))))
                .currency(transaction.getCurrency())
                .remarks(remarks)
                .connectionType(connectionType)
                .operator(operator)
                .build();
        log.info("Sending ft Robi preprocess response : {}", transactionResponse);
        return transactionResponse;
    }

    public IBUser getUserByUserId(String userId) throws ExceptionHandlerUtil {
        IBUser ibUser = getUserInfo.findByUserId(userId);
        mobileRechargeValidatorUseCase.validateUser(ibUser);
        return ibUser;
    }

    public List<MobileRechargeCustomerEntity> getCustomersByUserOid(String userOid) throws ExceptionHandlerUtil {
        MobileRechargeIbConsumerEntity ibConsumer = getUserInfo.findByIbUserOid(userOid);
        if (ibConsumer == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, CONSUMER_NOT_FOUND);
        }
        if (ibConsumer.getCustomers() == null || !ibConsumer.getCustomers().iterator().hasNext()) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, CUSTOMER_NOT_FOUND);
        }
        List<MobileRechargeCustomerEntity> customers = ibConsumer.getCustomers().stream()
                .filter(cus -> cus.getBankOid().equals(BANK_OID_35) && cus.getStatus().equalsIgnoreCase(OPERATIVE))
                .collect(Collectors.toList());

        if(customers == null || customers.size() < 1)
        {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, ACTIVE_CUSTOMER_NOT_FOUND);
        }
        return customers;
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

    public  List<MobileRechargeTransBeneficiaryEntity> getToMobileRechargeAccountInfo(String beneficiaryAccountNo) throws ExceptionHandlerUtil{
        List<MobileRechargeTransBeneficiaryEntity> beneficiaries = toAccountInfo.findByBeneficiaryAccountNo(beneficiaryAccountNo);

        if (beneficiaries == null || beneficiaries.size() == 0) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, BENEFICIARY_ACCOUNT_NOT_FOUND);
        }
        return beneficiaries;
    }

    void sendOtp(MobileRecargePreProcessRequest request, MobileRechargeTransaction transaction, IBUser ibUser) throws ExceptionHandlerUtil {
        if (!request.getOtpMethod().equalsIgnoreCase("sms")) {
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, INVALID_OTP_METHOD);
        }
        log.info("Sending request to pin server for otp sending");

        String offsetOtp = pinServerClient.sendOtpRequest(request.getRequestId(), "", request.getTraceId(), transaction.getTransactionOid(), ibUser.getMobileNo());
        if(offsetOtp == null){
            updateTransactionStatus(transaction, OTP_NOT_SENT, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, OTP_NOT_SENT);
        }
        log.info("Otp sent successfully");
        transaction.setOffsetOtp(offsetOtp);
        transaction.setTransStatus(TRANSACTION_STATUS_OTP_SENT);
        updateTransactionProcess.updateState(transaction);
    }

    public void updateTransactionStatus(MobileRechargeTransaction transactionDetails, String failureReason, String status) throws ExceptionHandlerUtil {
        transactionDetails.setFailureReason(failureReason);
        transactionDetails.setTransStatus(status);
        transactionDetails.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        updateTransactionProcess.updateState(transactionDetails);
    }

    void calculateCharge(String accountNo, String productOid, String branchOid, BigDecimal transactionAmount, MobileRechargeTransaction rechargeTransaction) throws ExceptionHandlerUtil {
        log.info("Send request calculate charge information");

        ResponseEntity<AbsClientService.ChargeModelResponse> chargeModelResponse = chargeModelClient.getChargeModelResponse(accountNo, productOid, branchOid, "ft-intra", transactionAmount);
        log.info("Received response for calculate charge information {}", chargeModelResponse);

        if(!chargeModelResponse.getStatusCode().is2xxSuccessful() || !chargeModelResponse.getBody().getHeader().getResponseCode().equals("200")) {
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, chargeModelResponse.getBody().getHeader().getResponseMessage());
        }
        rechargeTransaction.setChargeAmount(chargeModelResponse.getBody().getBody().getData().getChargeAmount());
        rechargeTransaction.setVatAmount(chargeModelResponse.getBody().getBody().getData().getVatAmount());
    }

}
