package net.celloscope.api.securityQuestion.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionVerificationTrailJpaEntity;
import net.celloscope.api.securityQuestion.adapter.out.persistence.repository.SecurityQuestionVerificationTrailRepository;
import net.celloscope.api.securityQuestion.application.port.out.LoadSecurityQuestionVerificationTrailPort;
import net.celloscope.api.securityQuestion.application.port.out.SaveSecurityQuestionVerificationTrailPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityQuestionVerificationTrailAdapter implements SaveSecurityQuestionVerificationTrailPort, LoadSecurityQuestionVerificationTrailPort {

    private final SecurityQuestionVerificationTrailRepository securityQuestionVerificationTrailRepository;


    @Override
    public SecurityQuestionVerificationTrailJpaEntity saveSecurityQuestionVerificationTrail(SecurityQuestionVerificationTrailJpaEntity securityQuestionVerifiacationTralJpaEntity) {
      return securityQuestionVerificationTrailRepository.save(securityQuestionVerifiacationTralJpaEntity);
    }

    @Override
    public int loadSecurityQuestionVerificationTrailForUser(String referenceOid) throws ExceptionHandlerUtil {
        int countByReferenceOid = securityQuestionVerificationTrailRepository.countByReferenceOid(referenceOid);
        if(countByReferenceOid == 0){
            log.error("NO SecurityQuestionVerificationTrailJpaEntity FOUND for user referenceOid: {}",referenceOid);
        }
        return countByReferenceOid;
    }


}
