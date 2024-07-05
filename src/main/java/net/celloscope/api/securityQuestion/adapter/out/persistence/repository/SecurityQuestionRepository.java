package net.celloscope.api.securityQuestion.adapter.out.persistence.repository;

import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestionJpaEntity,String> {

    Optional<SecurityQuestionJpaEntity> findByIbUserOid(String ibUserOid);
}
