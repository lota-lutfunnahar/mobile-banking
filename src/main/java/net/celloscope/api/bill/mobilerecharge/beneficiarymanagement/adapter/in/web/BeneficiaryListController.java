package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListResponse;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.BeneficiaryListUseCase;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
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
public class BeneficiaryListController {

    private final BeneficiaryListUseCase beneficiaryListUseCase;

    public BeneficiaryListController(BeneficiaryListUseCase beneficiaryListUseCase) {
        this.beneficiaryListUseCase = beneficiaryListUseCase;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public BeneficiaryListResponse getBeneficiaryList(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
             Principal principal

            )
    {
        try{
            log.info("Beneficiary List is requested for: {}", principal.getName());

            BeneficiaryListResponse response = beneficiaryListUseCase.getBeneficiaries(
                    BeneficiaryListRequest.builder()
                            .userId(principal.getName())
                            .requestId(requestId)
                            .requestTime(requestTime)
                            .build()
            );

            log.info("Beneficiary List Response data: {}", response);
            return response;

        } catch (ExceptionHandlerUtil e){
            log.error(e.getMessage());
            throw new ResponseStatusException(e.getCode(), e.getMessage());
        }

    }
}
