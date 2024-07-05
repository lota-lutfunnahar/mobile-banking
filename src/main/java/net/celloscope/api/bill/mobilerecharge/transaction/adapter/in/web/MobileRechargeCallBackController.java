package net.celloscope.api.bill.mobilerecharge.transaction.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeCallBackUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionCallBackResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RestController
public class MobileRechargeCallBackController {
    private final MobileRechargeCallBackUseCase callBackUseCase;

    public MobileRechargeCallBackController(MobileRechargeCallBackUseCase callBackUseCase) {
        this.callBackUseCase = callBackUseCase;
    }

    @PostMapping(path = "/call-back/robi/ft-request-process", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RobiTransactionCallBackResponse> robiTransactionRequestsCallBack(
            @Valid @RequestBody RobiTransactionCallBackRequest request
    ) throws Exception {
        try {

            log.info("Request received for ft robi call back: {} ", request);
            ResponseEntity<RobiTransactionCallBackResponse> response = callBackUseCase.getCallBack(request);
            log.info("Response send for ft robi call back: {} ", response);
            return response;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }
}
