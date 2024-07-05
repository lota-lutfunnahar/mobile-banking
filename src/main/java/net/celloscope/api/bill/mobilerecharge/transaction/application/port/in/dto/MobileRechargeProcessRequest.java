package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileRechargeProcessRequest {


    @NotNull(message = "toAccount can not be null")
    @NotEmpty(message = "toAccount can not be null")
    @Pattern(regexp = "^01[3-9]\\d{8}$", message = "To account is not valid")
    private String toAccount;

    @NotNull(message = "Operator can't be empty.")
    @Pattern(regexp = "ROBI|GP|BANGLALINK|TELETALK", message = "Invalid Operator.")
    private String operator;

    @NotNull(message = "Connection Type can't be empty.")
    @Pattern(regexp = "PREPAID|POSTPAID", message = "Invalid Connection Type.")
    private String connectionType;

    @NotNull
    private String otp;

    @NotNull
    private String transactionRequestRefId;

    @NotNull
    private String accountId;

    private String requestId;
    private String traceId;
    private String userId;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
