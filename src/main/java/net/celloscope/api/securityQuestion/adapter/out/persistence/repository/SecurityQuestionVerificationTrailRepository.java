package net.celloscope.api.securityQuestion.adapter.out.persistence.repository;

import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionVerificationTrailJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityQuestionVerificationTrailRepository extends JpaRepository<SecurityQuestionVerificationTrailJpaEntity,String> {

    int countByReferenceOid(String referenceOid);

}
