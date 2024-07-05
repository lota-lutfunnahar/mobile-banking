package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.UpdateMobileRechargeBeneficiaryUseCase;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateRequest;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.MobileRechargeBeneficiaryUpdateResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/v1/beneficiaries/mobilerecharge")
public class UpdateMobileRechargeBeneficiaryController {
    private final UpdateMobileRechargeBeneficiaryUseCase updateMobileRechargeBeneficiaryUseCase;

    public UpdateMobileRechargeBeneficiaryController(UpdateMobileRechargeBeneficiaryUseCase updateMobileRechargeBeneficiaryUseCase) {
        this.updateMobileRechargeBeneficiaryUseCase = updateMobileRechargeBeneficiaryUseCase;
    }

    @PutMapping(value = "/{beneficiaryOid}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public MobileRechargeBeneficiaryUpdateResponse updateBeneficiary(
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
             Principal principal,
            @PathVariable @NotNull String beneficiaryOid,
            @Valid @RequestBody MobileRechargeBeneficiaryUpdateRequest request
            )
    {
        request.setBeneficiaryOid(beneficiaryOid);
        try {
            log.info("Request to update mobile recharge beneficiary {}", request);
            MobileRechargeBeneficiaryUpdateResponse response = updateMobileRechargeBeneficiaryUseCase.updateBeneficiary(request);
            log.info("Response to update mobile recharge beneficiary {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage());
        }
    }
}
