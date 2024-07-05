package net.celloscope.api.securityQuestion.adapter.in.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.branch.dto.BranchListResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.application.port.in.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping("/v1/security-questions")
public class SecurityQuestionController {

 private final ValidateSecurityQuestionUseCase validateSecurityQuestionUseCase;
 private final LoadSecurityQuestionUseCase loadSecurityQuestionUseCase;


    @GetMapping(value = "/{ibUserOid}")
    public ResponseEntity<LoadSecurityQuestonResponseEntity> getSecurityQuestion(
            @PathVariable("ibUserOid") @NotBlank(message = "ibUserOid  can not be null") @NotEmpty(message = "ibUserOid  can not be empty")  String ibUserOid

    ){
        try{
            log.info("Request received for getting Security question for ibUserOid: {}", ibUserOid);
            List<QuestionWithoutAnswer> questionWithoutAnswerList = loadSecurityQuestionUseCase.loadSecurityQuestionForUser(ibUserOid);
            ResponseEntity<LoadSecurityQuestonResponseEntity> response = new ResponseEntity<>(
                    LoadSecurityQuestonResponseEntity.builder()
                            .data(questionWithoutAnswerList)
                            .userMessage("Security questions list successfully retrieved")
                            .build(), HttpStatus.OK);
            log.info("Response send for getting Security question for ibUserOid: {}, response: {}", ibUserOid, response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }



    @PostMapping("/verify")
    public ResponseEntity<ValidateSecurityQuestionResponseEntity> verifySecurityQuestion(
             @RequestBody ValidateSecurityQuestionCommand request
    ){
        try{
            log.info("Request received for verifying Security question for reference id: {}, user id: {}, request {}",request.getReferenceOid(), request.getIbUserOid(), request);
            boolean validateSecurityQuestion = validateSecurityQuestionUseCase.validateSecurityQuestion(request);
            log.info("Response send for verifying  Security question for reference id: {}, user id: {}, result {}", request.getReferenceOid(), request.getIbUserOid(), validateSecurityQuestion);
            return new ResponseEntity<>(ValidateSecurityQuestionResponseEntity.builder()
                    .userMessage( "security questions matched" )
                    .build(), HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }




}
