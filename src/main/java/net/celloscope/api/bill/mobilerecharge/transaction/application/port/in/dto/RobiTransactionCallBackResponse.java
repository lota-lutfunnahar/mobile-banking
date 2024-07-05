package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RobiTransactionCallBackResponse {private String userMessage;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
