package net.celloscope.api.securityQuestion.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionUseCase;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionPort;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveSecurityQuestionService implements SaveSecurityQuestionUseCase {
    private final SaveSecurityQuestionPort saveSecurityQuestionPort;

    @Override
    public boolean saveSecurityQuestionForUser(SaveSecurityQuestionCommand saveSecurityQuestionCommand) throws ExceptionHandlerUtil {
        return saveSecurityQuestionPort.saveSecurityQuestionForUser(saveSecurityQuestionCommand);
    }

}
