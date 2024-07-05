package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class LastMobileRechargeBeneficiaryResponse {
    List<MobileRechargeBeneficiary> data;
    String userMessage;
}
