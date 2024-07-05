package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDto {
    String transactionRefId;
    String transactionDate;
    String creditAccountTitle;
    String debitAccount;
    String creditAccount;
    String remarks;
    String operatorType;
    String connectionType;
    BigDecimal debitAmount;
    String bankOid;
    String transferStatus;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
