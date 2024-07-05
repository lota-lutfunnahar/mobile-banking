package net.celloscope.api.securityQuestion.adapter.out.persistence;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionVerificationTrailJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.repository.SecurityQuestionVerificationTrailRepository;
import net.celloscope.api.securityQuestion.application.service.SaveSecurityQuestionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SecurityQuestionVerificationTrailAdapterTest {

    @Mock
    SecurityQuestionVerificationTrailRepository securityQuestionVerificationTrailRepository;

    @InjectMocks
    SecurityQuestionVerificationTrailAdapter securityQuestionVerificationTrailAdapter;

    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";
    private final String referenceOid = "32978c0f613c";



    @Test
    void contextLoading() {
        assertNotNull(SecurityQuestionVerificationTrailAdapter.class);
    }


    @Test
    void saveSecurityQuestionVerificationTrail() {
        Mockito.when(securityQuestionVerificationTrailRepository.save(Mockito.any()))
                .thenReturn(buildSecurityQuestionVerificationTrailJpaEntity());
        SecurityQuestionVerificationTrailJpaEntity securityQuestionVerificationTrailJpaEntity = securityQuestionVerificationTrailAdapter
                .saveSecurityQuestionVerificationTrail(buildSecurityQuestionVerificationTrailJpaEntity());
        Assertions.assertThat(securityQuestionVerificationTrailJpaEntity)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(buildSecurityQuestionVerificationTrailJpaEntity());
    }


    @Test
    void loadSecurityQuestionVerificationTrailForUser() throws ExceptionHandlerUtil {
        Mockito.when(securityQuestionVerificationTrailRepository.countByReferenceOid(Mockito.any()))
                .thenReturn(2);
        int trailFound = securityQuestionVerificationTrailAdapter.loadSecurityQuestionVerificationTrailForUser(referenceOid);
        assertEquals(trailFound,2);
    }

    private SecurityQuestionVerificationTrailJpaEntity buildSecurityQuestionVerificationTrailJpaEntity() {
        return SecurityQuestionVerificationTrailJpaEntity.builder()
                .ibUserOid(ibUserOid)
                .referenceOid(referenceOid)
                .userProvidedData("[{\"key\":\"photoId\",\"answer\":\"1234234234\"},{\"key\":\"dateOfBirth\",\"answer\":\"05/06/1999\"},{\"key\":\"fathersName\",\"answer\":\"mr. xyz\"}]")
                .build();
    }
}