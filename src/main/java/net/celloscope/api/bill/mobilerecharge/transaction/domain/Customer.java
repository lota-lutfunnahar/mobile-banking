package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.celloscope.api.selfAccountCreation.entity.SelfAccountCreationPersonEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


@Getter
@RequiredArgsConstructor
@Data
public class Customer {

    private String customerOid;

    private String bankCustomerId;

    private String mnemonic;

    private String gender;

    private String fullName;

    private String mobileNo;

    private Date dateOfBirth;

    private String bankOid;

    private String status;

    private String currentVersion;

    private Timestamp createdOn;

    private Timestamp editedOn;

    private String traceId;

    private Set<Account> accounts;

    private IbConsumer ibconsumer;

    Set<SelfAccountCreationPersonEntity> persons;

    private String language;

    private String nationality;

    private String cbSectorCode;

    private String cbIndustryCode;

    private String residence;

    private String sector;

    private String target;

    private String customerType;

    private String bankCustomerIdCreateRef;

    private Timestamp bankCustomerIdCreateOn;
}
