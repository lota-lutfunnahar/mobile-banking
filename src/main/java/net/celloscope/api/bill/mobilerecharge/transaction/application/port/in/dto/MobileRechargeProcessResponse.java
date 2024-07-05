package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileRechargeProcessResponse {
    private String referenceNo;
    private String transactionTime;
    private String userMessage;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
