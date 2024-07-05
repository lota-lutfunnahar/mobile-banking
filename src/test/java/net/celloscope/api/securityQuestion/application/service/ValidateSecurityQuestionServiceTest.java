package net.celloscope.api.securityQuestion.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.metaProperty.entity.MetaProperty;
import net.celloscope.api.metaProperty.service.MetaPropertyHelperService;
import net.celloscope.api.securityQuestion.application.port.in.ValidateSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionVerificationTrailPort;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionVerificationTrailPort;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.celloscope.api.core.util.Messages.SECURITY_QUESTION_CHECK_TIMELIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateSecurityQuestionServiceTest {

    @Mock
    LoadSecurityQuestionPort loadSecurityQuestionPort;

    @Mock
    LoadSecurityQuestionVerificationTrailPort loadSecurityQuestionVerificationTrailPort;

    @Mock
    SaveSecurityQuestionVerificationTrailPort saveSecurityQuestionVerificationTrailPort;

    @Mock
    MetaPropertyHelperService metaPropertyService;

    @InjectMocks
    ValidateSecurityQuestionService validateSecurityQuestionService;

    private final String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";
    private final String referenceOid = "32978c0f613c";

    @Value("${metaData.security-question.code}")
    private String securityQuestionMetaCode;

    @Value("${metaData.security-question.name}")
    private String securityQuestionMetaName;

    private final MetaProperty metaPropertyEntity = MetaProperty.builder()
            .method("Absolute")
                .sortOrder(1)
                .name("SECURITY QUESTION ATTEMPT LIMIT THRESHOLD")
                .value(Arrays.asList("3"))
            .build();
    @Test
    void contextLoading() {
        assertNotNull(ValidateSecurityQuestionService.class);
    }



    @Test
    void shouldValidateSecurityQuestionForUserWhileValidateSecurityQuestionIsCalled() throws ExceptionHandlerUtil, IOException {
        Mockito.when(loadSecurityQuestionPort.loadSecurityQuestionForUser(ibUserOid))
                .thenReturn(buildSecurityQuestion());
        when(metaPropertyService.getValueJson(securityQuestionMetaCode, securityQuestionMetaName)).thenReturn(Arrays.asList(metaPropertyEntity));

        boolean b = validateSecurityQuestionService.validateSecurityQuestion(buildValidateSecurityQuestionCommand());
        assertTrue(b);
    }

    @Test
    void shouldReturnQuestionListWhileLoadSecurityQuestionForUserIsCalled() throws ExceptionHandlerUtil {
        Mockito.when(loadSecurityQuestionPort.loadSecurityQuestionForUser(ibUserOid))
                .thenReturn(buildSecurityQuestion());
        List<Question> questionList = validateSecurityQuestionService.loadSecurityQuestionForUser(ibUserOid);
        List<Question> questions = buildQuestionList();
        org.assertj.core.api.Assertions.assertThat(questionList)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(questions);
    }

    @Test
    void shouldReturnTrueWhenIsEligibleToCheckCalledWithEligibleToCheckValueLessThan3() throws ExceptionHandlerUtil {
        when(metaPropertyService.getValueJson(securityQuestionMetaCode, securityQuestionMetaName)).thenReturn(Arrays.asList(metaPropertyEntity));

        Mockito.when(loadSecurityQuestionVerificationTrailPort.loadSecurityQuestionVerificationTrailForUser(referenceOid))
                .thenReturn(0);
        boolean eligibleToCheck = validateSecurityQuestionService.isEligibleToCheck(referenceOid);
        assertTrue(eligibleToCheck);
    }

    @Test
    void shouldThrowExceptionWhenIsEligibleToCheckCalledWithEligibleToCheckValueMoreThan2() throws ExceptionHandlerUtil {
        when(metaPropertyService.getValueJson(securityQuestionMetaCode, securityQuestionMetaName)).thenReturn(Arrays.asList(metaPropertyEntity));
        Mockito.when(loadSecurityQuestionVerificationTrailPort.loadSecurityQuestionVerificationTrailForUser(referenceOid))
                .thenReturn(3);
        Throwable thrown = assertThrows(ExceptionHandlerUtil.class, () -> {
            validateSecurityQuestionService.isEligibleToCheck(referenceOid);
        });
        assertEquals(SECURITY_QUESTION_CHECK_TIMELIMIT, thrown.getMessage());
    }


    SecurityQuestion buildSecurityQuestion() {
        return SecurityQuestion.builder()
                .ibUserOid("a66e86a3-3c72-4460-903e-32978c0f613c")
                .questionAndAnswer(buildQuestionList())
                .build();
    }



    List<Question> buildQuestionList() {
        List<Question> list = new ArrayList<>();
        list.add(Question.builder().question("What is your NID").key("photoId").answer("6725804371061").build());
        list.add(Question.builder().question("What is your Date of Birth").key("dateOfBirth").answer("04/03/1971").build());
        list.add(Question.builder().question("What is your MobileNo").key("mobileNo").answer("01844074030").build());
        return list;
    }


    ValidateSecurityQuestionCommand buildValidateSecurityQuestionCommand() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/test/java/net/celloscope/api/securityQuestion/common/securityQuestionRequestBody.json"), ValidateSecurityQuestionCommand.class);
    }




}