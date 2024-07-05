package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileRechargeBeneficiaryDetailsResponse {
    private String beneficiaryOid;
    private String beneficiaryType;
    private String shortName;
    private String description;
    private String beneficiaryAccountNo;
    private String operator;
    private String connectionType;
    private String currency;
}
