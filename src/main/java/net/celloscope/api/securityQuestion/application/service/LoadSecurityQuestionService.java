package net.celloscope.api.securityQuestion.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.application.port.in.LoadSecurityQuestionUseCase;
import net.celloscope.api.securityQuestion.application.port.in.QuestionWithoutAnswer;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadSecurityQuestionService  implements LoadSecurityQuestionUseCase {

    private final LoadSecurityQuestionPort loadSecurityQuestionPort;

    ModelMapper modelMapper = new ModelMapper();



    @Override
    public List<QuestionWithoutAnswer> loadSecurityQuestionForUser(String ibUserOid) throws ExceptionHandlerUtil {
        SecurityQuestion securityQuestions = loadSecurityQuestionPort.loadSecurityQuestionForUser(ibUserOid);
        log.info("loadSecurityQuestionForUser result for ibUserOid: {}, result: {}", ibUserOid, securityQuestions);
        return securityQuestions.getQuestionAndAnswer().stream()
                .map(question -> modelMapper
                        .map(question, QuestionWithoutAnswer.class))
                .collect(Collectors.toList());
    }


}
