package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Map;

public interface MobileRechargeProcessUseCase {
    ResponseEntity<Map<String, Object>> transactionProcess(MobileRechargeProcessRequest request, String requestTimeoutInSeconds) throws ExceptionHandlerUtil, JSONException, ParseException;

}
