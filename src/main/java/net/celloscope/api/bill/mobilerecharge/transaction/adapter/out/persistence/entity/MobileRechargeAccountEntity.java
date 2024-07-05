package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;
import net.celloscope.api.customerInfo.entity.CustomerEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class MobileRechargeAccountEntity {

    @Id
    @Expose
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="accountoid", updatable = false, nullable = false)
    private String accountOid;

    @Column(name="bankaccountno") @Expose
    private String bankAccountNo;

    @Column(name="mnemonic") @Expose
    private String mnemonic;

    @Column(name="accounttitle") @Expose
    private String accountTitle;

    @Column(name="productoid") @Expose
    private String productOid;

    @Column(name="productname") @Expose
    private String productName;

    @Column(name="producttype") @Expose
    private String productType;

    @Column(name="currency") @Expose
    private String currency;

    @Column(name="lastknownbalance") @Expose
    private BigDecimal lastKnownBalance;

    @Column(name="lastknownbalanceasof") @Expose
    private Timestamp lastKnownBalanceAsOf;

    @Column(name="jointholderjson") @Expose
    private String jointHolderJson;

    @Column(name = "postingrestrict") @Expose
    private String postingRestrict;

    @Column(name="cbsaccountstatus") @Expose
    private String cbsAccountStatus;

    @Column(name="bankoid") @Expose
    private String bankOid;

    @Column(name="branchoid") @Expose
    private String branchOid;

    @Column(name="status") @Expose
    private String status;

    @Column(name="currentversion") @Expose
    private String currentVersion;

    @Column(name="cbscurrentversion") @Expose
    private String cbsCurrentVersion;

    @Column(name="createdon") @Expose
    private Timestamp createdOn;

    @Column(name="editedon") @Expose
    private Timestamp editedOn;


    @JsonBackReference
    @ManyToMany(mappedBy = "accounts")
    Set<MobileRechargeCustomerEntity> customers;

    @Column(name="initialdeposit") @Expose
    private BigDecimal initialDeposit;

    @Column(name="nomineejson") @Expose
    private String nomineeJson;

    @Column(name="aoijson") @Expose
    private String aoiJson;

    @Column(name="bankaccountnocreateref") @Expose
    private String bankAccountNoCreateRef;

    @Column(name="bankaccountnocreatedon") @Expose
    private Timestamp bankAccountNoCreatedOn;

    @Column(name="traceid") @Expose
    private String traceId;

    @Override
    public String toString(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this);
    }

}
