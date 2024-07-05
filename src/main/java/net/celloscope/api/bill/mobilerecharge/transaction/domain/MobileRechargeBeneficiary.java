package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MobileRechargeBeneficiary {

    private String beneficiaryAccountNo;

    private String shortName;

    private String accountTitle;

    private String beneficiaryType;

    private String ibUserOid;

    private String description;

    private String productCode;

    private String productName;

    private String bankOid;

    private String branchCode;

    private String branchName;

    private String currency;

    private String mobileNo;

    private String email;

    private String status;

    private String createdBy;

    private Timestamp createdOn;

    private Timestamp editedOn;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
