package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryListRequest {
    private String requestId;
    private String requestTime;
    private String traceId;
    private String userId;
}
