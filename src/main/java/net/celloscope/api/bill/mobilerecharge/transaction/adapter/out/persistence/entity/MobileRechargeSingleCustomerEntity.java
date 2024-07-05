package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class MobileRechargeSingleCustomerEntity {
    @Id
    @Expose
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="customeroid", updatable = false, nullable = false)
    private String customerOid;

    @Column(name = "bankcustomerid") @Expose
    private String bankCustomerId;

    @Column(name = "mnemonic") @Expose
    private String mnemonic;

    @Column(name = "gender") @Expose
    private String gender;

    @Column(name="fullname") @Expose
    private String fullName;

    @Column(name="mobileno") @Expose
    private String mobileNo;

    @Column(name="dateofbirth") @Expose
    private Date dateOfBirth;

    @Column(name="bankoid") @Expose
    private String bankOid;

    @Column(name="status") @Expose
    private String status;

    @Column(name="currentversion") @Expose
    private String currentVersion;

    @Column(name="createdon") @Expose
    private Timestamp createdOn;

    @Column(name="editedon") @Expose
    private Timestamp editedOn;

    @Column(name="traceid") @Expose
    private String traceId;

    @Column(name = "language") @Expose
    private String language;

    @Column(name="nationality") @Expose
    private String nationality;

    @Column(name="cbsectorcode") @Expose
    private String cbSectorCode;

    @Column(name="cbindustrycode") @Expose
    private String cbIndustryCode;

    @Column(name="residence") @Expose
    private String residence;

    @Column(name="sector") @Expose
    private String sector;

    @Column(name="target") @Expose
    private String target;

    @Column(name="customertype") @Expose
    private String customerType;

    @Column(name="bankcustomeridcreateref") @Expose
    private String bankCustomerIdCreateRef;

    @Column(name="bankcustomeridcreatedon") @Expose
    private Timestamp bankCustomerIdCreateOn;

    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this);
    }
}
