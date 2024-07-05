package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.account.entity.AccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeProcessUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeReverseFtUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.*;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import net.celloscope.api.transaction.entity.TransactionEntity;
import net.celloscope.api.user.entity.IbUserEntity;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static net.celloscope.api.core.util.Messages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MobileRechargeReverseFTService implements MobileRechargeReverseFtUseCase {
    private final GetBalanceInfo balanceInfo;
    private final SavePreProcessTransaction processTransaction;
    private final CoreCorrectFtRequest coreCorrectFtRequest;
    private final UpdateTransactionProcess updateTransactionProcess;


    @Value("${robi.settlement.bank.account.number}")
    private String robiSettlementBankAccountNo;


    @Override
    public void reverseTransaction(MobileRechargeTransaction transaction, MobileRechargeProcessRequest request, List<MobileRechargeCustomerEntity> customer, Account fromAccount, IBUser ibUser, String traceId) throws ExceptionHandlerUtil, JSONException, ParseException {
        log.info("Request for reverse ft for fail robi request {}",  transaction.getTransactionOid());
        BigDecimal workingBalance = balanceInfo.getBalance(robiSettlementBankAccountNo);
        MobileRechargeTransaction rechargeTransaction = saveReverseTransaction(request, customer, fromAccount, ibUser, traceId, transaction);
//        robiTransactionValidator.isBalanceGraterThenTotal(workingBalance, request.getAmount()
//                .add(transaction.getChargeAmount().add(transaction.getVatAmount())));
        CBSFtResponse cbsResponseReverseObject = coreCorrectFtRequest.sendCoreCorrectFtRequest(rechargeTransaction, "Yes",
                robiSettlementBankAccountNo.substring(0, 4), robiSettlementBankAccountNo,
                fromAccount.getBranchOid(), fromAccount.getBankAccountNo(),
        rechargeTransaction.getTransAmount(), traceId);

        Date cbsTransactionReverseDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").parse(cbsResponseReverseObject.getCbsTransactionDate());
        updateCbsReferenceNoAndCbsTraceNo(cbsResponseReverseObject.getReferenceNo(), cbsResponseReverseObject.getCbsTraceNo(), cbsTransactionReverseDate,
                cbsResponseReverseObject.getTransactionRequestDate(), rechargeTransaction);

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


    public MobileRechargeTransaction saveReverseTransaction(MobileRechargeProcessRequest request, List<MobileRechargeCustomerEntity> customer, Account fromAccount,
                                                            IBUser userEntity, String traceId, MobileRechargeTransaction transaction) throws ExceptionHandlerUtil {

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
                .transAmount(transaction.getTransAmount())
                .chargeAmount(BigDecimal.valueOf(0.00).setScale(2))
                .vatAmount(BigDecimal.valueOf(0.00).setScale(2))
                .transStatus("PreProcess")
                .bankOid(BANK_OID_35)
                .requestId(request.getRequestId())
                .ibUserOid(userEntity.getIbUserOid())
                .remarks(ROBI_FT_REVERSE_REMARKS)
                .traceId(traceId)
                .debitedAccount(robiSettlementBankAccountNo)
                .debitedAccountTitle("Robi settlement ac")
                .debitedAccountBranchOid(robiSettlementBankAccountNo.substring(0, 4))
                .creditedAccount(fromAccount.getBankAccountNo())
                .creditedAccountTitle(fromAccount.getAccountTitle())
                .creditedAccountCustomerOid(customer.get(0).getCustomerOid())
                .creditedAccountCbsCustomerId(customer.get(0).getBankCustomerId())
//                .customerAccount(request.getToAccount())
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(userEntity.getUserId())
                .intermediateStatus(intermediateStatusDto)
                .currency("BDT")
                .build();
        rechargeTransaction = processTransaction.saveTransaction(rechargeTransaction);;
        log.info("Robi transaction pre-process data saved : {}", rechargeTransaction);
        return rechargeTransaction;
    }
}
