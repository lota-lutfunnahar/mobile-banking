package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.MobileRechargeOtpVerificationTrail;
import net.celloscope.api.mobileVerificationForAccountCreation.entity.OtpVerificationTrailEntity;

public interface SaveOtpVerificationTrailData {
    void saveData(MobileRechargeOtpVerificationTrail verificationTrail);
}
