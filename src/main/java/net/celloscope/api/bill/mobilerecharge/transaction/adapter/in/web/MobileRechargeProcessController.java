package net.celloscope.api.bill.mobilerecharge.transaction.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeProcessUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRechargeProcessRequest;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Map;

@Slf4j
@Validated
@RestController
public class MobileRechargeProcessController {
    private final MobileRechargeProcessUseCase mobileRechargeProcessUseCase;

    public MobileRechargeProcessController(MobileRechargeProcessUseCase mobileRechargeProcessUseCase) {
        this.mobileRechargeProcessUseCase = mobileRechargeProcessUseCase;
    }

    @PostMapping(path = "/v1/banks/35/accounts/transaction-request-types/transaction-requests", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Map<String, Object>> robiMobileRechargeProcess(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
             Principal principal,
            @RequestHeader(name = "Trace-Id", required = true) String traceId,
            @Valid @RequestBody MobileRechargeProcessRequest request
    ) throws Exception {
        try {
            request.setRequestId(requestId);
            request.setUserId(principal.getName());
            request.setTraceId(traceId);
            log.info("Request received for ft robi transaction {} by user ID {}", request, principal.getName());
            ResponseEntity<Map<String, Object>> response = mobileRechargeProcessUseCase.transactionProcess(request, requestTimeoutInSeconds);
            log.info("Response send for ft robi transaction {} for user ID {}", response, principal.getName());
            return response;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }
}
