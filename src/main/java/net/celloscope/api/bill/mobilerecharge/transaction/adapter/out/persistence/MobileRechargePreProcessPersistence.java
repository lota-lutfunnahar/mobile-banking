package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransactionEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository.MobileRechargeTransactionRepository;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.SavePreProcessTransaction;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.TransactionInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.UpdateTransactionProcess;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MobileRechargePreProcessPersistence implements SavePreProcessTransaction, UpdateTransactionProcess, TransactionInfo {

    private final TransactionMapper transactionMapper ;
    private final MobileRechargeTransactionRepository transactionRepository;

    @Override
    public MobileRechargeTransaction saveTransaction(MobileRechargeTransaction transaction) throws ExceptionHandlerUtil {
        MobileRechargeTransactionEntity rechargeTransactionEntity = new MobileRechargeTransactionEntity();
        rechargeTransactionEntity = transactionRepository.save(transactionMapper.mapToJpaEntity(rechargeTransactionEntity, transaction));
        return transactionMapper.mapToDomainEntity(rechargeTransactionEntity);
    }

    @Override
    public MobileRechargeTransaction updateState(MobileRechargeTransaction rechargeTransaction) throws ExceptionHandlerUtil {
        MobileRechargeTransactionEntity rechargeEntity = transactionRepository.findByRequestId(rechargeTransaction.getRequestId());
        rechargeEntity = transactionRepository.save(transactionMapper.mapToJpaEntity(rechargeEntity, rechargeTransaction));
        return transactionMapper.mapToDomainEntity(rechargeEntity);
    }

    @Override
    public MobileRechargeTransaction findByTransactionOid(String refId) throws ExceptionHandlerUtil {
        MobileRechargeTransactionEntity rechargeEntity = transactionRepository.findByTransactionOid(refId);
        if(rechargeEntity == null){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found RobiTransactionId: " + refId);
        }
        return transactionMapper.mapToDomainEntity(rechargeEntity);
    }
}
