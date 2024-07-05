package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransactionEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final ObjectMapper objectMapper;

    public MobileRechargeTransaction mapToDomainEntity(MobileRechargeTransactionEntity robiMobileRechargeEntity) throws ExceptionHandlerUtil {
        MobileRechargeTransaction rechargeTransaction = new MobileRechargeTransaction();
        BeanUtils.copyProperties(robiMobileRechargeEntity, rechargeTransaction);
        try {
            rechargeTransaction.setIntermediateStatus(objectMapper.readValue(robiMobileRechargeEntity.getIntermediateStatus(), TransactionIntermediateStatus.class));
        } catch (JsonProcessingException e) {
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
        return rechargeTransaction;
    }

    public MobileRechargeTransactionEntity mapToJpaEntity(MobileRechargeTransactionEntity robiMobileRechargeEntity, MobileRechargeTransaction robiRechargeTransaction) throws ExceptionHandlerUtil {
        BeanUtils.copyProperties(robiRechargeTransaction, robiMobileRechargeEntity, "transactionOid, createdBy, createdOn");
        robiMobileRechargeEntity.setCreatedOn(robiMobileRechargeEntity.getCreatedOn() == null ? Timestamp.valueOf(LocalDateTime.now()) : robiMobileRechargeEntity.getCreatedOn());
        robiMobileRechargeEntity.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        try {
            robiMobileRechargeEntity.setIntermediateStatus(objectMapper.writeValueAsString(robiRechargeTransaction.getIntermediateStatus()));
        } catch (JsonProcessingException e) {
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
        return robiMobileRechargeEntity;
    }
}
