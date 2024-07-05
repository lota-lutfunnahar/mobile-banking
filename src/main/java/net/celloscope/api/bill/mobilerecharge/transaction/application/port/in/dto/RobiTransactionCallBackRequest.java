package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RobiTransactionCallBackRequest {

    @NotNull(message = "currency can not be null")
    @NotEmpty(message = "currency can not be null")
    private String requestId;


    @NotNull(message = "robiTransactionRefId can not be null")
    @NotEmpty(message = "robiTransactionRefId can not be null")
    private String robiTransactionRefId;

    @NotNull(message = "toAccount can not be null")
    @NotEmpty(message = "toAccount can not be null")
    @Pattern(regexp = "^01[3-9]\\d{8}$", message = "To account is not valid")
    private String toAccount;

    @NotNull(message = "amount can not be null")
    @Min(1)
    private BigDecimal amount;

    @NotNull(message = "currency can not be null")
    @NotEmpty(message = "currency can not be null")
    private String currency;

    private String status;
    private String errorCode;
    private String errorMessage;
    private String remarks;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
