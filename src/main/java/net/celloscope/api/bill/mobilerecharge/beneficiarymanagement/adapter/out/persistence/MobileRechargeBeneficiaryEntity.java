package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "mobilerechargebeneficiary")
public class MobileRechargeBeneficiaryEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "mobilerechargebeneficiaryoid", updatable = false, nullable = false)
    private String mobileRechargeBeneficiaryOid;

    @Column(name = "ibuseroid")
    private String userId;

    @Column(name = "shortname")
    private String shortName;

    private String description;

    @Column(name = "beneficiarytype")
    private String beneficiaryType;

    @Column(name = "beneficiaryaccountno")
    private String beneficiaryAccountNo;

    @Column(name = "accounttitle")
    private String accountTitle;

    private String currency;

    @Column(name = "connectiontype")
    private String connectionType;

    private String operator;
    private String email;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdon")
    @Builder.Default
    private Date createdOn = new Date();

    @Column(name = "editedby")
    private String editedBy;

    @Column(name = "editedon")
    private Date editedOn;

    @Column(name = "currentversion")
    @Builder.Default
    private String currentVersion = "1";

    private String status;
}
