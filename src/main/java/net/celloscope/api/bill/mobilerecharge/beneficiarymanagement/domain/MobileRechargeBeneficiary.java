package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MobileRechargeBeneficiary {
    private String beneficiaryOid;
    private String beneficiaryAccountNo;
    private String operator;
    private String connectionType;
    private String shortName;
    private String currency;
    private final String beneficiaryType = "mobile recharge";
    private String userId;
    private String description;
}
