package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface MobileRechargeCallBackUseCase {
    ResponseEntity<RobiTransactionCallBackResponse> getCallBack(RobiTransactionCallBackRequest request) throws ExceptionHandlerUtil, JSONException, ParseException;

}
