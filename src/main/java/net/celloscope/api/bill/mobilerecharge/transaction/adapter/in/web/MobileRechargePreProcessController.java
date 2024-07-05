package net.celloscope.api.bill.mobilerecharge.transaction.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargePreProcessUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.MobileRecargePreProcessResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@Slf4j
@Validated
@RestController
public class MobileRechargePreProcessController {
    private final MobileRechargePreProcessUseCase mobileReachrgePreProcessUseCase;

    public MobileRechargePreProcessController(MobileRechargePreProcessUseCase mobileReachrgePreProcessUseCase) {
        this.mobileReachrgePreProcessUseCase = mobileReachrgePreProcessUseCase;
    }

    @PostMapping(path = "/v1/banks/35/accounts/transaction-request-types/transaction-requests-pre-process", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<MobileRecargePreProcessResponse> robiMobileRechargePreProcess(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestHeader(name = "Trace-Id", required = false) String traceId,
             Principal principal,
            @Valid @RequestBody MobileRecargePreProcessRequest request) throws Exception {
        try {
            if (StringUtils.isEmpty(traceId)) {
                HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                traceId = String.valueOf(httpServletRequest.getAttribute("Trace-Id"));
            }
            request.setRequestId(requestId);
            request.setUserId(principal.getName());
            request.setTraceId(traceId);
//            request.setRemarks(request.getRemarks().trim());
            log.info("Pre process robi transaction request {} : ", request);
            ResponseEntity<MobileRecargePreProcessResponse> response = mobileReachrgePreProcessUseCase.transactionPreProcess(request);
            log.info("Pre process robi transaction response {} : ", response);
            return response;
        } catch (ExceptionHandlerUtil ex) {
            throw new ResponseStatusException(ex.code, ex.getMessage());
        }
    }
}
