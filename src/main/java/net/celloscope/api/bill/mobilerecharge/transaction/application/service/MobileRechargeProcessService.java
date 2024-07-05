package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeProcessUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeReverseFtUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiMobileRechargeResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.*;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeOtpVerificationTrail;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.client.PinServerClient;
import net.celloscope.api.core.client.RedisClient;
import net.celloscope.api.core.client.TransactionProfileClient;
import net.celloscope.api.core.service.MobileRechargeClientService;
import net.celloscope.api.core.service.RobiClientService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Utility;
import net.celloscope.api.metaProperty.entity.MetaProperty;
import net.celloscope.api.metaProperty.service.MetaPropertyHelperService;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import net.celloscope.api.mobileVerificationForAccountCreation.repository.OtpVerificationTrailRepository;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileRechargeProcessService implements MobileRechargeProcessUseCase {

    private final GetUserInfo getUserInfo;
    private final GetProductInfo productInfo;
    private final TransactionInfo transactionInfo;
    private final GetToAccountInfo getAccountInfo;
    private final MobileRechargeValidatorUseCase mobileRechargeValidatorUseCase;
    private final CoreCorrectFtRequest coreCorrectFtRequest;
    private final UpdateTransactionProcess updateTransactionProcess;
    private final MobileRechargeReverseFtUseCase rechargeReverseFtUseCase;
    private final MobileRechargeClientService rechargeClientService;
    private final SaveOtpVerificationTrailData trailData;
    private final ModelMapper mapper;


    private static List<MetaProperty> retryOtpValidityMetaData = new ArrayList<>();

    private final OtpVerificationTrailRepository otpVerificationTrailRepository;
    private final ValidCountReferenceStatus countReferenceStatus;

    private final MetaPropertyHelperService metaPropertyService;
    private final PinServerClient pinServerClient;
    private final RedisClient redisClient;
    private final TransactionProfileClient tpClient;

    @Value("${robi.settlement.bank.account.number}")
    private String robiSettlementBankAccountNo;

    @Value("${metaData.otp-retry.code}")
    private String retryOtpValidityMetaCode;

    @Value("${metaData.otp-retry.name}")
    private String retryOtpValidityMetaName;

    @Override
    public ResponseEntity<Map<String, Object>> transactionProcess(MobileRechargeProcessRequest request, String requestTimeoutInSeconds) throws ExceptionHandlerUtil, JSONException, ParseException {
        log.info("Mobile Recharge process request {}", request);
        IBUser user = getUserByUserId(request.getUserId());
        List<MobileRechargeCustomerEntity> customers = getCustomersByUserOid(user.getIbUserOid());
        Account fromAccount = getAccountInfo(request.getAccountId());
        MobileRechargeCustomerEntity customer = customers.get(0);

        mobileRechargeValidatorUseCase.validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(fromAccount);


        log.info("From Account : {}", fromAccount.toString());
        log.info("Validating user : {}", user.toString());
        mobileRechargeValidatorUseCase.validateUser(user);
        mobileRechargeValidatorUseCase.isDebitCreditAccountDifferent(fromAccount, request.getToAccount());
        mobileRechargeValidatorUseCase.isAccountActive(fromAccount, "From");
        mobileRechargeValidatorUseCase.isAccountTransactional(fromAccount, "From");
        log.info("validation successful");


        MobileRechargeTransaction transaction = getTransactionDetails(request.getTransactionRequestRefId());
        verifyOtp(request.getRequestId(), requestTimeoutInSeconds, request.getTraceId(), request.getOtp(), transaction, user.getIbUserOid());
        verifyTransactionalHash(request.getTransactionRequestRefId(), request.getTraceId(), fromAccount.getBankAccountNo(),
                request.getToAccount(), transaction.getTransAmount().setScale(0).toString(), user.getMobileNo(), transaction);

        updateTransactionStatus(transaction, null, "RequestReceived");

        CBSFtResponse cbsResponseObject = coreCorrectFtRequest.sendCoreCorrectFtRequest(transaction, "Yes",
                fromAccount.getBranchOid(), fromAccount.getBankAccountNo(),
                robiSettlementBankAccountNo.substring(0, 4), robiSettlementBankAccountNo, transaction.getTransAmount(), request.getTraceId());

        Date cbsTransactionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").parse(cbsResponseObject.getCbsTransactionDate());

        updateCbsReferenceNoAndCbsTraceNo(cbsResponseObject.getReferenceNo(), cbsResponseObject.getCbsTraceNo(), cbsTransactionDate,
                cbsResponseObject.getTransactionRequestDate(), transaction);

        RobiMobileRechargeResponse mobileRechargeResponse = null;
        try {
            mobileRechargeResponse = sendRobiFtRequest(transaction,
                    fromAccount.getBankAccountNo(),
                    request, cbsResponseObject, customers, fromAccount, user, request.getTraceId());
        }catch (ExceptionHandlerUtil ex){
            if(ex.getCode().equals(HttpStatus.FAILED_DEPENDENCY) || ex.getCode().equals(HttpStatus.REQUEST_TIMEOUT) || ex.getCode().equals(HttpStatus.LOCKED) || ex.getCode().equals(HttpStatus.FORBIDDEN)){
                return new ResponseEntity<>(
                        buildResponse(ex.getCode().equals(HttpStatus.FAILED_DEPENDENCY)  ? "Transaction reverse" : ex.getMessage(),
                        cbsResponseObject.getCbsTraceNo(),
                        cbsTransactionDate),
                        HttpStatus.FAILED_DEPENDENCY);
            }
            else throw ex;
        }


        log.info("Mobile Recharge process response {} ", mobileRechargeResponse);
        updateTransactionStatus(transaction, null, "OK");
        tpClient.updateTransactionProfileResponse(fromAccount.getBankAccountNo(), transaction.getTransAmount());
        return new ResponseEntity<Map<String, Object>>(buildResponse(TRANSACTION_SUCCESSFUL, mobileRechargeResponse.getTransactionId(), cbsTransactionDate), HttpStatus.OK);
//        return new ResponseEntity<Map<String, Object>>(buildResponse(TRANSACTION_SUCCESSFUL, "fgidgfdgkfhgkjfd", cbsTransactionDate), HttpStatus.OK);
    }

    Map<String, Object> buildResponse (String message, String referenceNo, Date cbsTransactionDate){
        Map<String, Object> response = new HashMap<>();
        response.put("userMessage", message);
        response.put("referenceNo", referenceNo);
        response.put("transactionTime", new SimpleDateFormat("EEEEE MMMMM dd, yyyy h:mm:ss aaa")
                .format(cbsTransactionDate));
        return response;
    }


    public void verifyOtp(String requestId, String requestTimeoutInSeconds, String traceId,
                          String otp, MobileRechargeTransaction transactionDetails, String ibUserOid) throws ExceptionHandlerUtil {
        log.info("Verifying otp for transaction {}", transactionDetails);
        int invalidCount = countReferenceStatus.countByReferenceOidAndStatusContainingIgnoreCase(transactionDetails.getTransactionOid(), INVALID);

        retryOtpValidityMetaData = metaPropertyService.getValueJson(retryOtpValidityMetaCode, retryOtpValidityMetaName);
        int validateTime = Integer.parseInt(retryOtpValidityMetaData.get(0).getValue().get(0));
        if (invalidCount >= validateTime) {
            updateTransactionStatus(transactionDetails, OTP_INVALID, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, OTP_INVALID_MULTIPLE_TIMES);
        }
        String otpValidationResponse = INVALID;
        try {
            log.info("Send otp verification request to pin server with opt {} for tracetid : {}", otp, traceId);
            otpValidationResponse = pinServerClient.sendVerifyOtpRequest
                    (requestId, requestTimeoutInSeconds, traceId, otp, transactionDetails.getTransactionOid(), transactionDetails.getOffsetOtp());
        } catch (ExceptionHandlerUtil e) {
            log.error("Error during verify OTP.", e.getMessage());
        }
        saveOtpVerificationTrailData(transactionDetails.getTransactionOid(), ibUserOid, otpValidationResponse);
        if (!otpValidationResponse.equalsIgnoreCase("valid")) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, OTP_INVALID);
        }
        log.info("Otp verified for {}", transactionDetails.getTransactionOid());
    }

    public void verifyTransactionalHash(String refId, String traceId, String fromAccount, String toAccount,
                                        String amount, String mobile, MobileRechargeTransaction transactionDetails) throws ExceptionHandlerUtil {
        String data = refId + fromAccount + toAccount + amount + mobile;
        String generatedHashValue = Utility.generateHash(data, traceId);
        String hashValue = redisClient.get(refId);
        if (!generatedHashValue.equals(hashValue)) {
            updateTransactionStatus(transactionDetails, TRANSACTION_INVALID, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, TRANSACTION_INVALID);
        }
        transactionDetails.getIntermediateStatus().setIsTransactionHashMatched(true);
        updateTransactionProcess.updateState(transactionDetails);
        redisClient.delete(transactionDetails.getTransactionOid());
        log.info("Transaction hash value verified for {}", transactionDetails.getTransactionOid());
    }

    public MobileRechargeTransaction getTransactionDetails(String refId) throws ExceptionHandlerUtil {
        MobileRechargeTransaction transaction = transactionInfo.findByTransactionOid(refId);
        if (transaction == null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, TRANSACTION_NOT_FOUND);
        }
        if (transaction.getTransStatus().equalsIgnoreCase(FAILED)) {
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, TRANSACTION_ALREADY_FAILED);
        }
        if (!transaction.getTransStatus().equalsIgnoreCase(TRANSACTION_STATUS_OTP_SENT)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, TRANSACTION_INVALID + " as status " + transaction.getTransStatus());
        }
        log.info("Transaction detail found {}", transaction);
        return transaction;
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


    public IBUser getUserByUserId(String userId) throws ExceptionHandlerUtil {
        IBUser ibUser = getUserInfo.findByUserId(userId);
        mobileRechargeValidatorUseCase.validateUser(ibUser);
        return ibUser;
    }

    public RobiMobileRechargeResponse sendRobiFtRequest(MobileRechargeTransaction transaction,
                                              String fromAccountNo, MobileRechargeProcessRequest request,
                                              CBSFtResponse cbsFtResponse,
                                              List<MobileRechargeCustomerEntity> customer, Account fromAccount,
                                              IBUser userEntity, String traceId) throws ExceptionHandlerUtil, ParseException, JSONException {

//        Date cbsTransactionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").parse(cbsFtResponse.getCbsTransactionDate());

        RobiClientService.RobiProcessRequest robiProcessRequest = RobiClientService.RobiProcessRequest.builder()
                .originatorConversationId(request.getTransactionRequestRefId()) // TODO updated transaction for originatorConversationId
                .requestId(transaction.getRequestId())
                .mobileNo(request.getToAccount())
                .amount(transaction.getTransAmount().intValue())
                .currency("BDT")
                .remarks(transaction.getRemarks())
                .connectionType(request.getConnectionType())
                .operator(request.getOperator())
                .bankOid(BANK_OID_35)
                .bankName(BANK_NAME_35)
                .channelName(I_BANKING)
                .userId(request.getUserId())
                .build();

        ResponseEntity<RobiMobileRechargeResponse> robiResponse = null;
        log.info("Sending robi request for transaction {}", robiProcessRequest);
        try {
            robiResponse = rechargeClientService.getRobiFtResponse(robiProcessRequest);
            transaction.getIntermediateStatus().setIsRobiRechargeSuccessful(true);
            log.info("Received robi response for transaction {}", robiResponse);
        } catch (ExceptionHandlerUtil e) {
            log.error("Request error during performing transaction from robi recharge");
            transaction.setFailureReason(e.getMessage());
            if (e.getCode().equals(HttpStatus.FAILED_DEPENDENCY) || e.getCode().equals(HttpStatus.NOT_ACCEPTABLE)
                    || e.getCode().equals(HttpStatus.TOO_MANY_REQUESTS) ) {
                rechargeReverseFtUseCase.reverseTransaction(transaction, request, customer, fromAccount, userEntity, traceId); //TODO reverse FT
                transaction.getIntermediateStatus().setIsRobiReverseFT(true);
                throw new ExceptionHandlerUtil(HttpStatus.FAILED_DEPENDENCY, e.getMessage());
            } else if (e.getCode().equals(HttpStatus.REQUEST_TIMEOUT)) {
                rechargeReverseFtUseCase.reverseTransaction(transaction, request, customer, fromAccount, userEntity, traceId);
                transaction.getIntermediateStatus().setIsRobiReverseFT(true);
                throw new ExceptionHandlerUtil(e.getCode(), e.getMessage());
            } else if (e.getCode().equals(HttpStatus.LOCKED)) {
                transaction.getIntermediateStatus().setIsRobiRechargeQueued(true);
                throw new ExceptionHandlerUtil(e.getCode(), e.getMessage());
            } else if (e.getCode().equals(HttpStatus.FORBIDDEN)) {
                transaction.getIntermediateStatus().setIsRobiRechargeFailed(true);
                throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, e.getMessage() + ", Amount will be reconciled later");
            }
            else {
                throw e;
            }
        } finally {
            transaction.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
            updateTransactionProcess.updateState(transaction);
        }
        log.info("response fro robi {} :", robiResponse.getBody());
        return robiResponse.getBody();
    }

    public void updateCbsReferenceNoAndCbsTraceNo(String cbsReferenceNo,
                                                 String cbsTraceNo,
                                                 Date cbsTransactionDate,
                                                 Date transactionRequestDate,
                                                  MobileRechargeTransaction transactionDetails) throws ExceptionHandlerUtil {
        if (Strings.isBlank(cbsReferenceNo)) {
            updateTransactionStatus(transactionDetails, FT_REFERENCE_NOT_FOUND, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, FT_REFERENCE_NOT_FOUND);
        }
        log.info("CBS reference value {} found for {}", cbsReferenceNo, transactionDetails.getTransactionOid());

        if (Strings.isBlank(cbsTraceNo)) {
            updateTransactionStatus(transactionDetails, FT_CBS_TRACE_NO_NOT_FOUND, FAILED);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, FT_CBS_TRACE_NO_NOT_FOUND);
        }
        log.info("CBS trace no. value {} found for {}", cbsTraceNo, transactionDetails.getTransactionOid());

        transactionDetails.setCbsReferenceNo(cbsReferenceNo);
        transactionDetails.setCbsTraceNo(cbsTraceNo);
        transactionDetails.setCbsTransactionDate(cbsTransactionDate);
        transactionDetails.setTransactionRequestDate(transactionRequestDate);

        updateTransactionStatus(transactionDetails, null, OK);
    }

    public void updateTransactionStatus(MobileRechargeTransaction transactionDetails, String failureReason, String status) throws ExceptionHandlerUtil {
        transactionDetails.setFailureReason(failureReason);
        transactionDetails.setTransStatus(status);
        transactionDetails.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        updateTransactionProcess.updateState(transactionDetails);
    }

    public void saveOtpVerificationTrailData(String refId, String userOid, String status) {
        MobileRechargeOtpVerificationTrail trail = MobileRechargeOtpVerificationTrail.builder()
                .referenceOid(refId)
                .ibUserOid(userOid)
                .requestTime(Timestamp.valueOf(LocalDateTime.now()))
                .status(status)
                .build();
        trailData.saveData(trail);
    }
}
