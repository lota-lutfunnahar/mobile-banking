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
public class LastRechargeResponseDto implements Serializable {
    private String status;
    private MobileRechargeDto data;
}
