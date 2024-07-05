package net.celloscope.api.securityQuestion.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "securityquestion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityQuestionJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "securityoid")
    private String securityOid;

    @Column(name = "questionandanswer")
    private String questionAndAnswer;

    @Column(name = "ibuseroid")
    private String ibUserOid;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "editedon")
    private Timestamp editedOn;

    @Column(name = "editedby")
    private String editedBy;

    @Column(name = "status")
    private String Status;

    @Column(name = "isnewrecord")
    private String isNewRecord;

    @Column(name = "currentversion")
    private String currentVersion;

    @Column(name = "traceid")
    private String traceId;

}
