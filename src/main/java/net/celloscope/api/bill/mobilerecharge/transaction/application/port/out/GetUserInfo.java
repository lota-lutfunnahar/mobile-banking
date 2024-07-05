package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IbConsumer;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface GetUserInfo {

    IBUser findByUserId(String userId) throws ExceptionHandlerUtil;

    MobileRechargeIbConsumerEntity findByIbUserOid(String ibUserOid) throws ExceptionHandlerUtil;
}
