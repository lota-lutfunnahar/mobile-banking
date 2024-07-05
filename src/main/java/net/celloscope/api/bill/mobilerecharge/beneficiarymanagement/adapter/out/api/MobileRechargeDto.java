package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MobileRechargeDto implements Serializable {
    private String mobileNo;
    private String operator;
    private String connectionType;
    private Integer amount;
    private String originatorConversationId;
    private String remarks;
    private String userId;
    private String bankOid;
    private String bankName;
    private String channelName;
}
