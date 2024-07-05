package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;
import net.celloscope.api.customerInfo.entity.CustomerEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class MobileRechargeSelfAccountCreationPersonEntity {
    @Id
    @Expose
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="personoid", updatable = false, nullable = false)
    private String personOid;

    @Column(name = "personid") @Expose
    private String personId ;

    @Column(name = "title") @Expose
    private String title    ;

    @Column(name = "fullname") @Expose
    private String fullName ;

    @Column(name = "firstname") @Expose
    private String firstName ;

    @Column(name = "middlename") @Expose
    private String middleName ;

    @Column(name = "lastname") @Expose
    private String lastName ;

    @Column(name = "gender") @Expose
    private String gender   ;

    @Column(name = "dateofbirth") @Expose
    private Date dateOfBirth   ;

    @Column(name = "placeofbirth") @Expose
    private String placeOfBirth  ;

    @Column(name = "countryofbirth") @Expose
    private String countryOfBirth       ;

    @Column(name = "fathername") @Expose
    private String fatherName ;

    @Column(name = "mothername") @Expose
    private String motherName ;

    @Column(name = "spousename") @Expose
    private String spouseName ;

    @Column(name = "maritalstatus") @Expose
    private String maritalStatus ;

    @Column(name = "nationality") @Expose
    private String nationality   ;

    @Column(name = "residentstatus") @Expose
    private String residentStatus       ;

    @Column(name = "occupation") @Expose
    private String occupation ;

    @Column(name = "monthlyincome") @Expose
    private BigDecimal monthlyIncome ;

    @Column(name = "photoidtype") @Expose
    private String photoIdType   ;

    @Column(name = "photoidno") @Expose
    private String photoIdNo ;

    @Column(name = "photoidissuancedate") @Expose
    private Date photoIdIssuanceDate  ;

    @Column(name = "photoidexpirationdate") @Expose
    private Date photoIdExpirationDate;

    @Column(name = "otherphotoidjson") @Expose
    private String otherPhotoIdJson     ;

    @Column(name = "etin") @Expose
    private String eTin     ;

    @Column(name = "presentaddress") @Expose
    private String presentAddress       ;

    @Column(name = "proofofaddress") @Expose
    private String proofOfAddress       ;

    @Column(name = "permanentaddress") @Expose
    private String permanentAddress     ;

    @Column(name = "ismobilenoverified") @Expose
    private String isMobileNoVerified   ;

    @Column(name = "mobileno") @Expose
    private String mobileNo ;

    @Column(name = "officephoneno") @Expose
    private String officePhoneNo ;

    @Column(name = "residencephoneno") @Expose
    private String residencePhoneNo     ;

    @Column(name = "email") @Expose
    private String email    ;

    @Column(name = "emergencycontactjson") @Expose
    private String emergencyContactJson ;

    @Column(name = "reverserelation") @Expose
    private String reverseRelation      ;

    @Column(name = "photopath") @Expose
    private String photoPath ;

    @Column(name = "photoidpathfront") @Expose
    private String photoIdPathFront     ;

    @Column(name = "photoidpathback") @Expose
    private String photoIdPathBack      ;

    @Column(name = "proofofaddressphotopath") @Expose
    private String proofOfAddressPhotoPath;

    @Column(name = "socialmedialink") @Expose
    private String socialMediaLink      ;

    @Column(name = "traceid") @Expose
    private String traceId  ;

    @Column(name = "currentversion") @Expose
    private String currentVersion       ;

    @Column(name = "cbscurrentversion") @Expose
    private String cbsCurrentVersion    ;

    @Column(name = "status") @Expose
    private String status   ;

    @Column(name="editedon") @Expose
    private Timestamp editedOn;

    @Column(name = "applicationdate") @Expose
    private Timestamp applicationDate      ;


    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "customerperson",
            joinColumns = @JoinColumn(name = "personoid", referencedColumnName = "personoid") ,
            inverseJoinColumns = @JoinColumn(name = "customeroid", referencedColumnName = "customeroid")
    )
    private Set<MobileRechargeCustomerEntity> customers;

    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this);
    }
}
