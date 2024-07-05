package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbUserEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransactionEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.*;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ObjectMapper objectMapper;

    public IBUser mapToDomainEntity(MobileRechargeIbUserEntity userEntity) throws ExceptionHandlerUtil {
        IBUser user = new IBUser();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    public IbConsumer mapToDomainIbConsumerEntity(MobileRechargeIbConsumerEntity userEntity) throws ExceptionHandlerUtil {
        IbConsumer ibConsumer = new IbConsumer();
//        ibConsumer.setCustomers(userEntity.getCustomers());
        BeanUtils.copyProperties(userEntity, ibConsumer);

        return ibConsumer;
    }

    MobileRechargeIbUserEntity mapToJpaEntity(MobileRechargeIbUserEntity userEntity, IBUser ibuser) throws ExceptionHandlerUtil {
        BeanUtils.copyProperties(ibuser, userEntity, "createdBy, createdOn");
        userEntity.setCreatedOn(userEntity.getCreatedOn() == null ? Timestamp.valueOf(LocalDateTime.now()) : userEntity.getCreatedOn());
        userEntity.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        return userEntity;
    }
}
