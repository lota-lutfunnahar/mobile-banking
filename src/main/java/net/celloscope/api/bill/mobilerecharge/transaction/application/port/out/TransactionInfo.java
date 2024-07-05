package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.transaction.entity.TransactionEntity;

public interface TransactionInfo {
    MobileRechargeTransaction findByTransactionOid(String refId) throws ExceptionHandlerUtil;
}
