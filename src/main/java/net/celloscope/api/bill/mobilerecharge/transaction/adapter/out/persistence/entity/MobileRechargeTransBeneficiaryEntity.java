package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.google.gson.GsonBuilder;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mobilerechargebeneficiary")
public class MobileRechargeTransBeneficiaryEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "mobilerechargebeneficiaryoid", updatable = false, nullable = false)
    private String beneficiaryOid;

    @NotNull
    @NotEmpty
    @Column(name = "beneficiaryaccountno")
    private String beneficiaryAccountNo;

    @NotNull
    @NotEmpty
    @Column(name = "shortname")
    private String shortName;


    @NotNull
    @NotEmpty
    @Column(name = "connectiontype")
    private String connectionType;

    @NotNull
    @NotEmpty
    @Column(name = "operator")
    private String operator;


    @NotNull
    @NotEmpty
    @Column(name = "accounttitle")
    private String accountTitle;

    @NotNull
    @NotEmpty
//    @Pattern(regexp = "(bkb)|(bkash)|(nagad)|(rocket)", message = "beneficiary type must be in  bkb, bkash, nagad, rocket")
    @Column(name = "beneficiarytype")
    private String beneficiaryType;

    @Column(name = "ibuseroid")
    private String ibUserOid;

    @Column(name = "description")
    private String description;

    @Column(name = "bankoid")
    private String bankOid;

    @Column(name = "branchoid")
    private String branchCode;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "currency")
    private String currency;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "editedby")
    private String editedBy;

    @Column(name = "currentversion")
    private String currentVersion;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name = "editedon")
    private Timestamp editedOn;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}