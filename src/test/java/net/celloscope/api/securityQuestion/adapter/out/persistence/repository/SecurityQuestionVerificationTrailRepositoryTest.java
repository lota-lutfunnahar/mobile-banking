package net.celloscope.api.securityQuestion.adapter.out.persistence.repository;

import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionVerificationTrailJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SecurityQuestionVerificationTrailRepositoryTest {

    @Autowired
    SecurityQuestionVerificationTrailRepository securityQuestionVerificationTrailRepository;

    private final String referenceOid = "32978c0f613c";

    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";

    @Test
    void countByReferenceOid() {
        securityQuestionVerificationTrailRepository.save(buildSecurityQuestionVerificationTrailJpaEntity());
        int countByReferenceOid = securityQuestionVerificationTrailRepository.countByReferenceOid(referenceOid);
        assertEquals(countByReferenceOid,1);
    }



    private SecurityQuestionVerificationTrailJpaEntity buildSecurityQuestionVerificationTrailJpaEntity() {
        return SecurityQuestionVerificationTrailJpaEntity.builder()
                .ibUserOid(ibUserOid)
                .referenceOid(referenceOid)
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .status("Success")
                .userProvidedData("[{\"key\":\"photoId\",\"answer\":\"1234234234\"},{\"key\":\"dateOfBirth\",\"answer\":\"05/06/1999\"},{\"key\":\"fathersName\",\"answer\":\"mr. xyz\"}]")
                .build();
    }
}