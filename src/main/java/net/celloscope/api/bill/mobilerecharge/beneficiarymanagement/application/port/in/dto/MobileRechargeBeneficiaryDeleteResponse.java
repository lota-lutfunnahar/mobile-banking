package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MobileRechargeBeneficiaryDeleteResponse {
    private String userMessage;
}
