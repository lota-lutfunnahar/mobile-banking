package net.celloscope.api.securityQuestion.adapter.out.persistence.repository;

import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SecurityQuestionRepositoryTest {

    @Autowired
    SecurityQuestionRepository securityQuestionRepository;

    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";



    @Test
    void findByIbUserOid() {
        SecurityQuestionJpaEntity securityQuestionJpaEntity = securityQuestionRepository.save(buildSecurityQuestionJpaEntity());
        Assertions.assertThat(securityQuestionJpaEntity)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("securityOid","createdOn")
                .isEqualTo(buildSecurityQuestionJpaEntity());
    }

    private SecurityQuestionJpaEntity buildSecurityQuestionJpaEntity() {
        return SecurityQuestionJpaEntity.builder()
                .ibUserOid(ibUserOid)
                .createdBy("System")
                .currentVersion("1")
                .isNewRecord("Yes")
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .securityOid("iuuiewri8734")
                .questionAndAnswer("[{\"key\":\"photoId\",\"question\":\"What is your NID\",\"answer\":\"1234234234\"},{\"key\":\"dateOfBirth\",\"question\":\"What is your Date of Birth\",\"answer\":\"05/06/1999\"},{\"key\":\"fathersName\",\"question\":\"What is your Father's name\",\"answer\":\"mr. xyz\"}]")
                .build();
    }
}