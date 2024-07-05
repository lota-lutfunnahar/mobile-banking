package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@Data
public class IbConsumer {

    private String ibUserOid;

    private String fullName;

    private String photoId;

    private String photoIdType;

    private Date dateOfBirth;

    private String mobileNo;

    private String address;

    private String email;

    private String currentVersion;

    private Timestamp createdOn;

    private Timestamp editedOn;

    private Set<Customer> customers;

    private String customerImageRef;

    private String nidFrontImageRef;

    private String nidBackImageRef;

    private String customerSignatureImageRef;

    private String cbsCustomerSignatureImageRef;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
