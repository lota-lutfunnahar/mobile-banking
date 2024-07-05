package net.celloscope.api.bill.mobilerecharge.transaction.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.RobiRechargePendingHistoryUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionHistoryListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@Slf4j
@RestController
public class RobiRechargePendingHistoryController {
    private final RobiRechargePendingHistoryUseCase robiRechargePendingHistoryUseCase;

    public RobiRechargePendingHistoryController(RobiRechargePendingHistoryUseCase robiRechargePendingHistoryUseCase) {
        this.robiRechargePendingHistoryUseCase = robiRechargePendingHistoryUseCase;
    }

    @GetMapping(path = "/v1/banks/{bankId}/mobile-recharge/transactions", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    RobiTransactionHistoryListResponse getRobiRechargePendingHistory(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
             Principal principal,
            @RequestHeader(name = "Trace-Id", required = true) String traceId
    ){
        log.info("Request Received for user: {}", principal.getName());
        RobiTransactionHistoryListResponse response = robiRechargePendingHistoryUseCase.getPendingResponse(new TransactionHistoryRequest(principal.getName(), "Pending", 20, 100));
        log.info("Response Sent: {}", response);
        return response;
    }
}
