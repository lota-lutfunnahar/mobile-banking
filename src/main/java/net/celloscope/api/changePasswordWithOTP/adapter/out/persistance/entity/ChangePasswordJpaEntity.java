package net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="changepasswordtrail")
public class ChangePasswordJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "changepasswordoid", updatable = false, nullable = false)
    private String changePasswordOid;

    @NotNull
    @Column(name = "ibuseroid")
    private String ibUserOid;

    @Column(name = "traceid")
    private String traceId;

    @Column(name = "requestid")
    private String requestId;

    @Column(name = "offsetotp")
    private String offsetOtp;

    @Column(name = "status")
    private String status;

    @Column(name = "otpresentcount")
    private int otpResentCount;

    @Column(name = "givenpassword")
    private String givenPassword;

    @Column(name = "otpsenton")
    private Timestamp otpSentOn;

    @Column(name = "otpverificationon")
    private Timestamp otpVerifiedOn;

    @Column(name = "referenceoid")
    private String refId;

    @Column(name = "failurereason")
    private String failureReason;

    @Column(name = "editedon")
    private Date editedOn;

    @Column(name = "editedby")
    private String editedBy;

    @Column(name = "createdon")
    private Date createdOn;

    @Column(name = "createdby")
    private String createdBy;
}
