package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.TransactionMapper;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransactionEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.CoreCorrectFtRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetBalanceInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.service.AbsClientService;
import net.celloscope.api.core.service.CorecorrectClientService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import net.celloscope.api.transaction.entity.TransactionEntity;
import net.celloscope.api.transaction.service.CoreCorrectFTRequestService;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class CoreCorrectApiPersistence implements GetBalanceInfo, CoreCorrectFtRequest {

    private final CorecorrectClientService corecorrectClientService;
    private final CoreCorrectFTRequestService coreCorrectFTRequestService;
    private final TransactionMapper transactionMapper;
    private final ModelMapper mapper;

    @Override
    public BigDecimal getBalance(String accountNo) throws ExceptionHandlerUtil {
        ResponseEntity<AbsClientService.CoreCorrectResponse> response = corecorrectClientService.getCorecorrectAdResponse(accountNo);
        return new BigDecimal(response.getBody().getBody().getAcWorkingBalance());
    }


    @Override
    public CBSFtResponse sendCoreCorrectFtRequest(MobileRechargeTransaction rechargeTransaction, String isIntraBranch, String branchOid,
                                                  String bankAccountNo, String settlementBranchOid, String settlementAccountNo,
                                                  BigDecimal amount, String traceId) throws JSONException, ExceptionHandlerUtil {

        MobileRechargeTransactionEntity transactionEntity = new MobileRechargeTransactionEntity();

        CBSFtResponse cbsResponseObject = coreCorrectFTRequestService.sendCoreCorrectFtRequest(mapper.map(transactionMapper.mapToJpaEntity(transactionEntity, rechargeTransaction), TransactionEntity.class), isIntraBranch,
                branchOid, bankAccountNo,  settlementBranchOid, settlementAccountNo, amount, traceId);

        return cbsResponseObject;
    }
}
