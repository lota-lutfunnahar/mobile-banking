package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeTransaction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.mfs.dto.CBSFtResponse;
import org.json.JSONException;

import java.math.BigDecimal;

public interface CoreCorrectFtRequest {
    CBSFtResponse sendCoreCorrectFtRequest(MobileRechargeTransaction rechargeTransaction, String isIntraBranch, String branchOid, String bankAccountNo, String settlementBranchOid, String settlementAccountNo, BigDecimal amount, String traceId) throws JSONException, ExceptionHandlerUtil;
}
