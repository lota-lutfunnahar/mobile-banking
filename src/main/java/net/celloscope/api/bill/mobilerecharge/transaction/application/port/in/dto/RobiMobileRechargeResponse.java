package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RobiMobileRechargeResponse {


    private String status;

    private String errorMessage;

    private String errorCode;

    private String transactionId;

    private Date transactionDate;

    private String reference;

    private String userMessage;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
