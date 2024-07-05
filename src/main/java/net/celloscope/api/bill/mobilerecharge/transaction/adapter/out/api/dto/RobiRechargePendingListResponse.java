package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobiRechargePendingListResponse {
    List<RobiRechargePendingResponse> data;
}
