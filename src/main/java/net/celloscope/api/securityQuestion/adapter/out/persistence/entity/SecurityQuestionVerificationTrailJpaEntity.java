package net.celloscope.api.securityQuestion.adapter.out.persistence.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "securityquestionverificationtrail")
public class SecurityQuestionVerificationTrailJpaEntity {

    @Id
    @GeneratedValue(generator =  "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "securityquestionverificationtrailoid", updatable = false, nullable = false)
    private String securityQuestionVerificationTrailOid;

    @Column(name = "referenceoid")
    private String referenceOid;

    @NotNull
    @Column(name = "ibuseroid")
    private String ibUserOid;

    @Column(name = "userprovideddata")
    private String userProvidedData;

    @Column(name = "status")
    private String status;

    @Column(name = "createdon")
    private Timestamp createdOn;


}
