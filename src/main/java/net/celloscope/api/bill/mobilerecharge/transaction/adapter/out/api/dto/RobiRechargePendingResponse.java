package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobiRechargePendingResponse {
    private String transactionRefId;
    private String transactionDate;
    private String originatorConversationId;
    private String remarks;
    private String status;
    private String traceNo;
}
