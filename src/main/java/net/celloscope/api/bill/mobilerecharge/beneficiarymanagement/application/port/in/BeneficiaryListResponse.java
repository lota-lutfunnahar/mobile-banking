package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import lombok.*;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryListResponse {
    private String userMessage;
    private List<MobileRechargeBeneficiary> data;
}
