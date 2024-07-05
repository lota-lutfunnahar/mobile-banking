package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import lombok.*;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class MobileRechargeOtpVerificationTrail {

    private String otpVerificationTrailOid;

    private String ibUserOid;

    private String referenceOid;

    private Timestamp requestTime;

    private Timestamp responseTime;

    private String machineIp;

    private String mac;

    private String status;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
