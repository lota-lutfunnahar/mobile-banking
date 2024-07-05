package net.celloscope.api.securityQuestion.application.service;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.SecurityQuestionVerificationTrailAdapter;
import net.celloscope.api.securityQuestion.application.port.in.QuestionWithoutAnswer;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LoadSecurityQuestionServiceTest {


     @Mock
     LoadSecurityQuestionPort loadSecurityQuestionPort;

    @InjectMocks
    LoadSecurityQuestionService loadSecurityQuestionService;


    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";

    @Test
    void contextLoading() {
        assertNotNull(LoadSecurityQuestionService.class);
    }


    @Test
    void loadSecurityQuestionForUser() throws ExceptionHandlerUtil {
        Mockito.when(loadSecurityQuestionPort.loadSecurityQuestionForUser(ibUserOid))
                .thenReturn(buildSecurityQuestion());
        List<QuestionWithoutAnswer> questionWithoutAnswers = loadSecurityQuestionService.loadSecurityQuestionForUser(ibUserOid);
        questionWithoutAnswers.forEach(System.out::println);
        Assertions.assertIterableEquals(buildQuestionWithoutAnswerList(),questionWithoutAnswers);
    }




    SecurityQuestion buildSecurityQuestion() {
        return SecurityQuestion.builder()
                .ibUserOid("367bcc69-5edf-41ea-8a26-921df4377a63")
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



    List<QuestionWithoutAnswer> buildQuestionWithoutAnswerList() {
        List<QuestionWithoutAnswer> list = new ArrayList<>();
        list.add(QuestionWithoutAnswer.builder().question("What is your NID").key("photoId").build());
        list.add(QuestionWithoutAnswer.builder().question("What is your Date of Birth").key("dateOfBirth").build());
        list.add(QuestionWithoutAnswer.builder().question("What is your Father's name").key("fathersName").build());
        return list;
    }
}