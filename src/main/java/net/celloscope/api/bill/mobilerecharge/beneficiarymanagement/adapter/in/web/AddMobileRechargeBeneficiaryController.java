package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.AddBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.AddBeneficiaryResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/v1/beneficiaries/mobilerecharge")
public class AddMobileRechargeBeneficiaryController {

    private final AddBeneficiaryUseCase addBeneficiaryUseCase;

    public AddMobileRechargeBeneficiaryController(AddBeneficiaryUseCase addBeneficiaryUseCase) {
        this.addBeneficiaryUseCase = addBeneficiaryUseCase;
    }

    @PostMapping(value = "/")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<AddBeneficiaryResponse> createMobileRechargeBeneficiary(
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestHeader(name = "Trace-Id", required = false) String traceId,
             Principal principal,
            @Valid @RequestBody AddBeneficiaryRequest request
            )
    {
        try{
            log.info("Request received for saving beneficiary data: {}", request);
            AddBeneficiaryResponse responseBody = addBeneficiaryUseCase.addBeneficiary(request, principal.getName());

            ResponseEntity<AddBeneficiaryResponse> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
            log.info("Response send for saving beneficiary data: {}", responseEntity);
            return responseEntity;
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage());
        }
    }

}
