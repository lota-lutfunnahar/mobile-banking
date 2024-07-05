package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileRechargeBeneficiaryUpdateResponse {
    private String beneficiaryOid;
    private String userMessage;
}
