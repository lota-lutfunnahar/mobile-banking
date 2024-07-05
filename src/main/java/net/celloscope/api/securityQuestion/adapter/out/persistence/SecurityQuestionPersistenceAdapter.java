package net.celloscope.api.securityQuestion.adapter.out.persistence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.repository.SecurityQuestionRepository;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;

import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionPort;
import net.celloscope.api.core.common.PersistenceAdapter;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;

import org.slf4j.MDC;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
public class SecurityQuestionPersistenceAdapter implements LoadSecurityQuestionPort, SaveSecurityQuestionPort {

    private final SecurityQuestionRepository securityQuestionRepository;

     ModelMapper modelMapper = new ModelMapper();
     ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SecurityQuestion loadSecurityQuestionForUser(String ibUserOid) throws ExceptionHandlerUtil {
        SecurityQuestionJpaEntity securityQuestionJpaEntity = securityQuestionRepository.findByIbUserOid(ibUserOid)
                .orElseThrow(() -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, Messages.NO_SECURITY_QUESTION_FOUND));
        return buildSecurityQuestion(securityQuestionJpaEntity);
    }

    @Override
    public boolean saveSecurityQuestionForUser(SaveSecurityQuestionCommand saveSecurityQuestionCommand) throws ExceptionHandlerUtil {
        SecurityQuestionJpaEntity questionJpaEntity = securityQuestionRepository.save(buildSecurityQuestionJpaEntity(saveSecurityQuestionCommand));
        log.info(" saved SecurityQuestionJpaEntity to DB: {}",questionJpaEntity);
        if(questionJpaEntity.getSecurityOid() == null){
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, Messages.SECURITY_QUESTION_FAILED_TO_SAVE);
        }
        return true;

    }


    private SecurityQuestion buildSecurityQuestion(SecurityQuestionJpaEntity securityQuestionJpaEntity) {
        SecurityQuestion securityQuestion = modelMapper.map(securityQuestionJpaEntity, SecurityQuestion.class);
        String jsonListString = securityQuestionJpaEntity.getQuestionAndAnswer();
        try {
            List<Question> questions = objectMapper.readValue(jsonListString, new TypeReference<List<Question>>() {});
            securityQuestion.setQuestionAndAnswer(questions);
            return securityQuestion;
        } catch (JsonProcessingException e) {
            log.error("Failed to process questionandanswer json list to Question list: {}",jsonListString);
        }
        return securityQuestion;
    }

        private SecurityQuestionJpaEntity buildSecurityQuestionJpaEntity(SaveSecurityQuestionCommand saveSecurityQuestionCommand) {
        SecurityQuestionJpaEntity securityQuestionJpaEntity = modelMapper.map(saveSecurityQuestionCommand, SecurityQuestionJpaEntity.class);
            securityQuestionJpaEntity.setQuestionAndAnswer(getQuestionAndAnswerAsString(saveSecurityQuestionCommand.getQuestionAndAnswer()));
            securityQuestionJpaEntity.setCreatedBy("System");
            securityQuestionJpaEntity.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            securityQuestionJpaEntity.setCurrentVersion("Active");
            securityQuestionJpaEntity.setIsNewRecord("Yes");
            securityQuestionJpaEntity.setCurrentVersion("1");
            securityQuestionJpaEntity.setTraceId(MDC.get("X-B3-TraceId"));

        return securityQuestionJpaEntity;
    }

    private String getQuestionAndAnswerAsString(List<Question> questionAndAnswerList)  {
        String questionAndAnswer = null;
        try {
            questionAndAnswer = objectMapper.writeValueAsString(questionAndAnswerList);
        } catch (JsonProcessingException e) {
            log.error("Failed to set questionAndAnswer field in securityQuestionJpaEntity  from Question list: {}",questionAndAnswerList);
        }
        return questionAndAnswer;
    }

}
