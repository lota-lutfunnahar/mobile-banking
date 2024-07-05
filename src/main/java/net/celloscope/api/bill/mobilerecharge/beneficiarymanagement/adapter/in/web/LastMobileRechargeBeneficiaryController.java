package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.LastMobileRechargeBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.LastMobileRechargeBeneficiaryResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/v1/beneficiaries/mobilerecharge")
public class LastMobileRechargeBeneficiaryController {
    private final LastMobileRechargeBeneficiaryUseCase lastMobileRechargeBeneficiaryUseCase;

    public LastMobileRechargeBeneficiaryController(LastMobileRechargeBeneficiaryUseCase lastMobileRechargeBeneficiaryUseCase) {
        this.lastMobileRechargeBeneficiaryUseCase = lastMobileRechargeBeneficiaryUseCase;
    }

    @RequestMapping(value = "/last_recharge", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public LastMobileRechargeBeneficiaryResponse getLastRechargedBeneficiary(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
             Principal principal
    ){
        log.info("Last Beneficiary Details is requested for: {}", principal.getName());
        try{
            LastMobileRechargeBeneficiaryResponse response = lastMobileRechargeBeneficiaryUseCase.getLastMobileRechargeBeneficiary(principal.getName());
            log.info("Response for Last beneficiary details request: {} ", response);
            if (response.getData().size() == 0) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.BENEFICIARY_ACCOUNT_NOT_FOUND);
            return response;
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage());
            throw new ResponseStatusException(ex.getCode(), ex.getMessage());
        }

    }

}
