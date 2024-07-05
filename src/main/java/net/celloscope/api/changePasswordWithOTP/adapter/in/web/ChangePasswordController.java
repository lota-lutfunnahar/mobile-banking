package net.celloscope.api.changePasswordWithOTP.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.changePasswordWithOTP.application.port.in.*;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.passwordRecovery.dto.PasswordRecoveryOtpResendRequestDto;
import net.celloscope.api.passwordRecovery.dto.PasswordRecoveryVerificationRequestDto;
import net.celloscope.api.user.dto.ChangePasswordRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/${api.version}/banks/{BankOId}/user/password")
@Validated
@RequiredArgsConstructor
public class ChangePasswordController extends RuntimeException {



   private final ChangePasswordUseCase changePasswordUseCase;
   private final VerifyOtpUseCase verifyOtpUseCase;
   private final ReSendOTPUseCase reSendOTPUseCase;



    @PostMapping(path = "/change-request", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Map<String, Object>> changePassword(
            @PathVariable("BankOId") @NotBlank(message = "BankOId  can not be null") @NotEmpty(message = "BankOId  can not be empty")  String BankOId,
            @Valid @RequestBody ChangePasswordRequest request,
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestHeader(name = "Trace-Id", required = false) String traceId,
            Principal principal
    ) {
        try{
            if (StringUtils.isEmpty(traceId)) {
                HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                traceId = String.valueOf(httpServletRequest.getAttribute("Trace-Id"));
            }
            log.info("Request received for password change: {}",request);
          Map<String,Object> response = changePasswordUseCase.updatePassword(request, principal.getName(),requestId,traceId);
            log.info("Response send for password change: {}" ,response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }





    @PostMapping(path = "/change-confirmation", consumes = "application/json", produces = "application/json")
    //@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Map<String, Object>> verifyOTP(
            @Valid @RequestBody VerifyOTPCommand command,
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            Principal principal
    ) throws Exception {
        try{
            log.info("Request received for password recovery verification: {}",command);
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String traceId = String.valueOf(httpServletRequest.getAttribute("Trace-Id"));
            verifyOtpUseCase.verifyOTP(command, requestId, principal.getName(), requestTimeoutInSeconds, traceId);
            Map<String, Object> response = new HashMap<>();
            response.put("userMessage","OTP verification completed successfully");
            log.info("Response send for password recovery verification: {}" ,response);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }



    @PostMapping(path = "/resend-otp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> resendOtp(
            @Valid @RequestBody ReSendOTPCommand reSendOTPCommand,
            @RequestHeader(name="Authorization") String token, Principal principal,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime

    ) throws Exception {
        try{
            log.info("Request received for password recovery: {}",reSendOTPCommand);
            Map<String, Object> response = reSendOTPUseCase.reSendOTP(principal.getName(), reSendOTPCommand);
            log.info("Response send for password recovery: {}" ,response);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }




}
