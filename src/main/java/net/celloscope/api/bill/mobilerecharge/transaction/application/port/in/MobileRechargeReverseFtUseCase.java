package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.json.JSONException;

import java.text.ParseException;
import java.util.List;

public interface MobileRechargeReverseFtUseCase {
    default void reverseTransaction(MobileRechargeTransaction transaction,
                                    MobileRechargeProcessRequest request,
                                    List<MobileRechargeCustomerEntity> customer, Account fromAccount,
                                    IBUser ibUser, String traceId) throws ExceptionHandlerUtil, JSONException, ParseException {

    }
}
