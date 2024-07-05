package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.ResponseEntity;

public interface MobileRechargePreProcessUseCase {
    ResponseEntity<MobileRecargePreProcessResponse> transactionPreProcess(MobileRecargePreProcessRequest request) throws ExceptionHandlerUtil;
}
