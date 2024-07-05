package net.celloscope.api.securityQuestion.adapter.out.persistence;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.repository.SecurityQuestionRepository;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.application.service.SaveSecurityQuestionService;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityQuestionPersistenceAdapterTest {

    @Mock
    SecurityQuestionRepository securityQuestionRepository;

    @InjectMocks
    SecurityQuestionPersistenceAdapter securityQuestionPersistenceAdapter;

    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";

    @Test
    void contextLoading() {
        assertNotNull(SecurityQuestionPersistenceAdapter.class);
    }
    


    @Test
    void shouldReturnValidSecurityQuestionWhenLoadSecurityQuestionForUserIsCalled() throws ExceptionHandlerUtil {
        Mockito.when(securityQuestionRepository.findByIbUserOid(ibUserOid))
                .thenReturn(buildSecurityQuestionJpaEntityOptional());
        SecurityQuestion securityQuestion = securityQuestionPersistenceAdapter.loadSecurityQuestionForUser(ibUserOid);
        Assertions.assertThat(securityQuestion)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("securityOid")
                .isEqualTo(buildSecurityQuestion());
    }

    @Test
    void ShouldSuccessToSaveSecurityQuestionWhileSaveSecurityQuestionForUserIsCalled() throws ExceptionHandlerUtil {

        Mockito.when(securityQuestionRepository.save(Mockito.any()))
                .thenReturn(buildSecurityQuestionJpaEntity());
        boolean b = securityQuestionPersistenceAdapter.saveSecurityQuestionForUser(buildSecurityQuestionCommand());
        assertTrue(b);

    }

    private Optional<SecurityQuestionJpaEntity> buildSecurityQuestionJpaEntityOptional() {
        return Optional.of(SecurityQuestionJpaEntity.builder()
                .ibUserOid(ibUserOid)
                .securityOid("iuuiewri8734")
                .questionAndAnswer("[{\"key\":\"photoId\",\"question\":\"What is your NID\",\"answer\":\"1234234234\"},{\"key\":\"dateOfBirth\",\"question\":\"What is your Date of Birth\",\"answer\":\"05/06/1999\"},{\"key\":\"fathersName\",\"question\":\"What is your Father's name\",\"answer\":\"mr. xyz\"}]")
                .build());
    }

    private SecurityQuestionJpaEntity buildSecurityQuestionJpaEntity() {
        return SecurityQuestionJpaEntity.builder()
                .ibUserOid(ibUserOid)
                .securityOid("iuuiewri8734")
                .questionAndAnswer("[{\"key\":\"photoId\",\"question\":\"What is your NID\",\"answer\":\"1234234234\"},{\"key\":\"dateOfBirth\",\"question\":\"What is your Date of Birth\",\"answer\":\"05/06/1999\"},{\"key\":\"fathersName\",\"question\":\"What is your Father's name\",\"answer\":\"mr. xyz\"}]")
                .build();
    }

    SecurityQuestion buildSecurityQuestion() {
        return SecurityQuestion.builder()
                .ibUserOid("a66e86a3-3c72-4460-903e-32978c0f613c")
                .questionAndAnswer(buildQuestionList())
                .build();
    }


    List<Question> buildQuestionList() {
        List<Question> list = new ArrayList<>();
        list.add(Question.builder().question("What is your NID").key("photoId").answer("1234234234").build());
        list.add(Question.builder().question("What is your Date of Birth").key("dateOfBirth").answer("05/06/1999").build());
        list.add(Question.builder().question("What is your Father's name").key("fathersName").answer("mr. xyz").build());
        return list;
    }

    List<Question> buildQuestionListWithoutQuestion() {
        List<Question> list = new ArrayList<>();
        list.add(Question.builder().key("photoId").answer("1234234234").build());
        list.add(Question.builder().key("dateOfBirth").answer("05/06/1999").build());
        list.add(Question.builder().key("fathersName").answer("mr. xyz").build());
        return list;
    }

    private SaveSecurityQuestionCommand buildSecurityQuestionCommand() {
        return SaveSecurityQuestionCommand.builder()
                .questionAndAnswer(buildQuestionListWithoutQuestion())
                .status("Active")
                .ibUserOid(ibUserOid)
                .build();

    }

}