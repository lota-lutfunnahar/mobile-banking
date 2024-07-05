package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileRecargePreProcessRequest {

    @NotNull(message = "toAccount can not be null")
    @NotEmpty(message = "toAccount can not be null")
    @Pattern(regexp = "^01[3-9]\\d{8}$", message = "To account is not valid")
    private String toAccount;

    @NotNull(message = "amount can not be null")
    private String amount;

    @NotNull(message = "currency can not be null")
    @NotEmpty(message = "currency can not be null")
    private String currency;

    @NotNull(message = "otpMethod can not be null")
    @NotEmpty(message = "otpMethod can not be null")
    @Pattern(regexp = "^(sms)|(email)$", message = "otpMethod must be sms or email")
    private String otpMethod;

    @NotNull(message = "Operator can't be empty.")
    @Pattern(regexp = "ROBI|GP|BANGLALINK|TELETALK", message = "Invalid Operator.")
    private String operator;

    @NotNull(message = "Connection Type can't be empty.")
    @Pattern(regexp = "PREPAID|POSTPAID", message = "Invalid Connection Type.")
    private String connectionType;

    @NotNull(message = "transactionType can not be null")
    private String transactionType;

    private String remarks;

    private String requestId;
    private String traceId;
    private String userId;
    private String accountId;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
