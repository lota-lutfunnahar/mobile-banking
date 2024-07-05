package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface SavePreProcessTransaction {
    MobileRechargeTransaction saveTransaction(MobileRechargeTransaction transaction) throws ExceptionHandlerUtil;
}
