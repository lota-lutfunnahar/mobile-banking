package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.*;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class MobileRechargeTransaction {

    private String transactionOid;

    private String transType;

    private BigDecimal transAmount;

    private BigDecimal chargeAmount;

    private BigDecimal vatAmount;

    private Date cbsTransactionDate;

    private Date transactionRequestDate;

    private String transStatus;

    private String failureReason;

    private String bankOid;

    private String requestId;

    private String cbsReferenceNo;

    private String narration;

    private String remarks;

    private String traceId;

    private String cbsTraceNo;

    private String debitedAccountOid;

    private String debitedAccount;

    private String debitedAccountCustomerOid;

    private String debitedAccountCbsCustomerId;

    private String debitedAccountBranchOid;

    private String creditedAccountOid;

    private String creditedAccount;

    private String customerAccount;

    private Date editedOn;

    private String creditedAccountCustomerOid;

    private String creditedAccountCbsCustomerId;

    private String creditedAccountBranchOid;

    private String currency;

    private String createdBy;

    private Date createdOn;

    private String offsetOtp;

    private int otpResentCount;

    private Timestamp otpSentOn;

    private Timestamp otpVerifiedOn;

    private String ibUserOid;

    private String creditedAccountTitle;

    private String debitedAccountTitle;

    private TransactionIntermediateStatus intermediateStatus;


    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
