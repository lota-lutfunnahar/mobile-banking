package net.celloscope.api.securityQuestion.application.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.metaProperty.entity.MetaProperty;
import net.celloscope.api.metaProperty.service.MetaPropertyHelperService;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionVerificationTrailJpaEntity;
import net.celloscope.api.securityQuestion.application.port.in.ValidateSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.application.port.in.ValidateSecurityQuestionUseCase;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionVerificationTrailPort;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionVerificationTrailPort;
import net.celloscope.api.core.common.UseCase;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class ValidateSecurityQuestionService implements ValidateSecurityQuestionUseCase {

    private final LoadSecurityQuestionPort loadSecurityQuestionPort;

    private final LoadSecurityQuestionVerificationTrailPort loadSecurityQuestionVerificationTrailPort;

    private final SaveSecurityQuestionVerificationTrailPort saveSecurityQuestionVerificationTrailPort;

    private final MetaPropertyHelperService metaPropertyService;

    @Value("${metaData.security-question.code}")
    private String securityQuestionMetaCode;

    @Value("${metaData.security-question.name}")
    private String securityQuestionMetaName;

    ModelMapper modelMapper = new ModelMapper();

    private List<MetaProperty> metaProperty = null;


    @Override
    public boolean validateSecurityQuestion(ValidateSecurityQuestionCommand command) throws ExceptionHandlerUtil {
        List<Question> storedQuestionList = loadSecurityQuestionForUser(command.getIbUserOid());
        log.info("storedQuestionList result for ibUserOid: {}, result: {}", command.getIbUserOid(), storedQuestionList);
        List<Question> providedQuestionList = command.getData();
        log.info("providedQuestionList by ibUserOid: {}, result: {}", command.getIbUserOid(), providedQuestionList);
        isEligibleToCheck(command.getReferenceOid());
        boolean isVerified = verifyProvidedAnswerWithStoredAnswer(providedQuestionList, storedQuestionList);
        saveVerificationTrailToDB(command, isVerified);
        if (!isVerified) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, Messages.SECURITY_QUESTION_NOT_MATCHED);
        }
        return true;
    }

    private boolean verifyProvidedAnswerWithStoredAnswer(List<Question> providedQuestionList, List<Question> storedQuestionList) {
        for (Question providedQuestion : providedQuestionList) {
            if (!verifySingleAnswer(providedQuestion, storedQuestionList))
                return false;
        }
        return true;
    }

    private boolean verifySingleAnswer(Question providedQuestion, List<Question> storedQuestionList) {
        return storedQuestionList.stream()
                .anyMatch(question -> question.getKey().equalsIgnoreCase(providedQuestion.getKey())
                        && question.getAnswer().equalsIgnoreCase(StringUtils.normalizeSpace(providedQuestion.getAnswer()))
                );
    }




    List<Question> loadSecurityQuestionForUser(String ibUserOid) throws ExceptionHandlerUtil {
        SecurityQuestion securityQuestions = loadSecurityQuestionPort.loadSecurityQuestionForUser(ibUserOid);
        return securityQuestions.getQuestionAndAnswer().stream()
                .map(question -> modelMapper.map(question, Question.class))
                .collect(Collectors.toList());
    }


    boolean isEligibleToCheck(String referenceOid) throws ExceptionHandlerUtil {
        int verificationTrailCount = loadSecurityQuestionVerificationTrailPort.loadSecurityQuestionVerificationTrailForUser(referenceOid);
        log.info("verificationTrailCount by referenceOid: {}, result: {}",referenceOid, verificationTrailCount);
        metaProperty = metaPropertyService.getValueJson(securityQuestionMetaCode, securityQuestionMetaName);
        if (verificationTrailCount >= Integer.parseInt(metaProperty.get(0).getValue().get(0))) {
            throw new ExceptionHandlerUtil(HttpStatus.FORBIDDEN, Messages.SECURITY_QUESTION_CHECK_TIMELIMIT);
        }
        return true;
    }

    private void saveVerificationTrailToDB(ValidateSecurityQuestionCommand command, boolean isVerified) {
        saveSecurityQuestionVerificationTrailPort.saveSecurityQuestionVerificationTrail(buildSecurityQuestionVerificationTrailJpaEntity(command, isVerified));
    }

    private SecurityQuestionVerificationTrailJpaEntity buildSecurityQuestionVerificationTrailJpaEntity(ValidateSecurityQuestionCommand command, boolean isVerified) {
        return SecurityQuestionVerificationTrailJpaEntity.builder()
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .referenceOid(command.getReferenceOid())
                .ibUserOid(command.getIbUserOid())
                .userProvidedData(command.getData().toString())
                .status(isVerified ? "Success" : "Failed")
                .build();

    }


}
