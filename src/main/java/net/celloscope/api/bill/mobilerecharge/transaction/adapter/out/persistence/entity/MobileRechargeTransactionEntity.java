package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.google.gson.GsonBuilder;
import lombok.*;
import net.celloscope.api.transaction.dto.TransactionIntermediateStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "transaction")
public class MobileRechargeTransactionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transactionoid", updatable = false, nullable = false)
    private String transactionOid;

    @Column(name = "transtype")
    private String transType;

    @Column(name = "transamount")
    private BigDecimal transAmount;

    @Column(name = "chargeamount")
    private BigDecimal chargeAmount;

    @Column(name = "vatamount")
    private BigDecimal vatAmount;

    @Column(name = "cbstransactiondate")
    private Date cbsTransactionDate;

    @Column(name = "transactionrequestdate")
    private Date transactionRequestDate;

    @Column(name = "transstatus")
    private String transStatus;

    @Column(name = "failurereason")
    private String failureReason;

    @Column(name = "bankoid")
    private String bankOid;

    @Column(name = "requestid")
    private String requestId;

    @Column(name = "cbsreferenceno")
    private String cbsReferenceNo;

    @Column(name = "narration")
    private String narration;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "traceid")
    private String traceId;

    @Column(name = "cbstraceno")
    private String cbsTraceNo;

    @Column(name = "debitedaccountoid")
    private String debitedAccountOid;

    @Column(name = "debitedaccount")
    private String debitedAccount;

    @Column(name = "debitedaccountcustomeroid")
    private String debitedAccountCustomerOid;

    @Column(name = "debitedaccountcbscustomerid")
    private String debitedAccountCbsCustomerId;

    @Column(name = "debitedaccountbranchoid")
    private String debitedAccountBranchOid;

    @Column(name = "creditedaccountoid")
    private String creditedAccountOid;

    @Column(name = "creditedaccount")
    private String creditedAccount;

    @Column(name = "customeraccount")
    private String customerAccount;

    @Column(name = "creditedaccountcustomeroid")
    private String creditedAccountCustomerOid;

    @Column(name = "creditedaccountcbscustomerid")
    private String creditedAccountCbsCustomerId;

    @Column(name = "creditedaccountbranchoid")
    private String creditedAccountBranchOid;

    @Column(name = "currency")
    private String currency;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdon")
    private Date createdOn;

    @Column(name = "editedby")
    private String editedBy;

    @Column(name = "editedon")
    private Date editedOn;

    @Column(name = "offsetotp")
    private String offsetOtp;

    @Column(name = "otpresentcount")
    private int otpResentCount;

    @Column(name="otpsenton")
    private Timestamp otpSentOn;

    @Column(name = "otpverifiedon")
    private Timestamp otpVerifiedOn;

    @Column(name = "ibuseroid")
    private String ibUserOid;

    @Column(name = "creditedaccounttitle")
    private String creditedAccountTitle;

    @Column(name = "debitedaccounttitle")
    private String debitedAccountTitle;

    @Column(name = "intermediatestatus")
    private String intermediateStatus;

    @Transient
    private TransactionIntermediateStatus intermediateStatusDto;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
