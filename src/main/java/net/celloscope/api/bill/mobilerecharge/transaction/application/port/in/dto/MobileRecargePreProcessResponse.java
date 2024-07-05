package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileRecargePreProcessResponse {
    private String userMessage;
    private String transactionRequestRefId;
    private String otpValidTimeInSec;
    private String fromAccountId;
    private String fromAccount;
    private String fromProductName;
    private String toAccountId;
    private String toAccount;
    private String toProductName;
    private String amount;
    private String vatAmount;
    private String chargeAmount;
    private String grandTotal;
    private String currency;
    private String connectionType;
    private String operator;
    private String remarks;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
