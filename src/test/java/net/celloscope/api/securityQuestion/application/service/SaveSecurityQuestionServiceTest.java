package net.celloscope.api.securityQuestion.application.service;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
class SaveSecurityQuestionServiceTest {

    @Autowired
    SaveSecurityQuestionService saveSecurityQuestionService;


    @Test
    void contextLoading() {
        assertNotNull(SaveSecurityQuestionService.class);
    }


    @Test
    void saveSecurityQuestionForUser() throws ExceptionHandlerUtil {
        boolean b = saveSecurityQuestionService.saveSecurityQuestionForUser(buildSecurityQuestion());
        assertTrue(b);
    }

    SaveSecurityQuestionCommand buildSecurityQuestion() {
        return SaveSecurityQuestionCommand.builder()
                .ibUserOid("367bcc69-5edf-41ea-8a26-921df4377a63")
                .questionAndAnswer(buildQuestionList())
                .status("Active")
                .build();
    }

    List<Question> buildQuestionList() {
        List<Question> list = new ArrayList<>();
        list.add(Question.builder().question("What is your NID").key("photoId").answer("1234234234").build());
        list.add(Question.builder().question("What is your Date of Birth").key("dateOfBirth").answer("05/06/1999").build());
        list.add(Question.builder().question("What is your Father's name").key("fathersName").answer("mr. xyz").build());
        return list;
    }
}