package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    String transactionRefId;
    String originatorConversationId;
    String transactionDate;
    String creditAccountTitle;
    String debitAccount;
    String creditAccount;
    String remarks;
    String operatorType;
    String connectionType;
    BigDecimal debitAmount;
    String bankOid;
    String status;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
