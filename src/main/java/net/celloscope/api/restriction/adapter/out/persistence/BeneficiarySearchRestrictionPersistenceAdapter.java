package net.celloscope.api.restriction.adapter.out.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.common.PersistenceAdapter;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.restriction.application.port.out.LoadUserRestrictionPort;
import net.celloscope.api.restriction.application.port.out.SaveUserRestrictionPort;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.repository.SecurityQuestionRepository;
import net.celloscope.api.securityQuestion.application.port.in.SaveSecurityQuestionCommand;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionPort;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionPort;
import net.celloscope.api.securityQuestion.domain.Question;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.modelmapper.ModelMapper;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
public class BeneficiarySearchRestrictionPersistenceAdapter implements LoadUserRestrictionPort, SaveUserRestrictionPort {

    private final BeneficiarySearchRestrictionRepository beneficiarySearchRestrictionRepository;

    @Override
    public BeneficiarySearchRestriction get(String userId) {
        return beneficiarySearchRestrictionRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean save(BeneficiarySearchRestriction beneficiarySearchRestriction) {
        beneficiarySearchRestrictionRepository.save(beneficiarySearchRestriction);
        return true;
    }
}
