package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobiTransactionHistoryListResponse {
    List<TransactionHistoryDto> data;
    int count;
    String message;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
