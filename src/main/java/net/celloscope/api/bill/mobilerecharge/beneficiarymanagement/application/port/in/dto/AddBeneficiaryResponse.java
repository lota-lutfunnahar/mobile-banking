package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddBeneficiaryResponse {

    private String beneficiaryOid;
    private String userMessage;
}
