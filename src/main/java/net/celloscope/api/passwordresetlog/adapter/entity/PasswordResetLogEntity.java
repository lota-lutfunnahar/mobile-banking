package net.celloscope.api.passwordresetlog.adapter.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passwordresetlog")
public class PasswordResetLogEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "passwordresetlogoid", updatable = false, nullable = false)
    private String passwordResetLogOid;
    @Column(name = "userid")
    private String userId;
    @Column(name = "ibuseroid")
    private String ibUserOid;
    @Column(name = "oldpassword")
    private String oldPassword;
    @Column(name = "newpassword")
    private String newPassword;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "createdon")
    private Timestamp createdOn;
    @Column(name = "editedby")
    private String editedBy;
    @Column(name = "editedon")
    private Timestamp editedOn;

}
