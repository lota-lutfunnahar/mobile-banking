package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    private String accountOid;

    private String bankAccountNo;

    private String mnemonic;

    private String accountTitle;

    private String productOid;

    private String productName;

    private String productType;

    private String currency;

    private BigDecimal lastKnownBalance;

    private Timestamp lastKnownBalanceAsOf;

    private String jointHolderJson;

    private String postingRestrict;

    private String cbsAccountStatus;

    private String bankOid;

    private String branchOid;

    private String status;

    private String currentVersion;

    private String cbsCurrentVersion;

    private Timestamp createdOn;

    private Timestamp editedOn;

    private BigDecimal initialDeposit;

    private String nomineeJson;

    private String aoiJson;

    private String bankAccountNoCreateRef;

    private Timestamp bankAccountNoCreatedOn;

    private String traceId;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
