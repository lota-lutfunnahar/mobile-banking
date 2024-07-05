package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

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
@Table(name="ibconsumer")
public class MobileRechargeIbConsumerEntity {

    @Id
    @Expose
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ibconsumeroid", updatable = false, nullable = false)
    private String ibConsumerOid;

    @Column(name="ibuseroid") @Expose
    private String ibUserOid;

    @Column(name="fullname") @Expose
    private String fullName;

    @Column(name="photoid") @Expose
    private String photoId;

    @Column(name="photoidtype") @Expose
    private String photoIdType;

    @Column(name="dateofbirth") @Expose
    private Date dateOfBirth;

    @Column(name="mobileno") @Expose
    private String mobileNo;

    @Column(name="address") @Expose
    private String address;

    @Column(name="email") @Expose
    private String email;

    @Column(name="currentversion") @Expose
    private String currentVersion;

    @Column(name="createdon") @Expose
    private Timestamp createdOn;

    @Column(name="editedon") @Expose
    private Timestamp editedOn;


    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "ibconsumer")
    private Set<MobileRechargeCustomerEntity> customers;

    @Column(name="customerimageref") @Expose
    private String customerImageRef;

    @Column(name="nidfrontimageref") @Expose
    private String nidFrontImageRef;

    @Column(name="nidbackimageref") @Expose
    private String nidBackImageRef;

    @Column(name="customersignatureimageref") @Expose
    private String customerSignatureImageRef;

    @Column(name="cbscustomersignatureimageref") @Expose
    private String cbsCustomerSignatureImageRef;


    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this);
    }


}