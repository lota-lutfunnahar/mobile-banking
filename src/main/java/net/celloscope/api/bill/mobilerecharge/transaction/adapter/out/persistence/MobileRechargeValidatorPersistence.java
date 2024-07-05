package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.SaveOtpVerificationTrailData;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.ValidCountReferenceStatus;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeOtpVerificationTrail;
import net.celloscope.api.mobileVerificationForAccountCreation.entity.OtpVerificationTrailEntity;
import net.celloscope.api.mobileVerificationForAccountCreation.repository.OtpVerificationTrailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static net.celloscope.api.core.util.Messages.INVALID;

@Component
@RequiredArgsConstructor
class MobileRechargeValidatorPersistence implements ValidCountReferenceStatus, SaveOtpVerificationTrailData {

    private final OtpVerificationTrailRepository otpVerificationTrailRepository;
    private final ModelMapper mapper;

    @Override
    public int countByReferenceOidAndStatusContainingIgnoreCase(String TransactionOid, String invalid) {
        int invalidCount = otpVerificationTrailRepository.countByReferenceOidAndStatusContainingIgnoreCase(TransactionOid, invalid);
        return invalidCount;
    }

    @Override
    public void saveData(MobileRechargeOtpVerificationTrail verificationTrail) {
        otpVerificationTrailRepository.save(mapper.map(verificationTrail, OtpVerificationTrailEntity.class));
    }
}
