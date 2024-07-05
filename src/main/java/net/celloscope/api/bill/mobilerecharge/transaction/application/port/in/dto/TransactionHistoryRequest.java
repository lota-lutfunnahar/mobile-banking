package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import lombok.Getter;

@Getter
public class TransactionHistoryRequest {
    private String userId;
    private String status;
    private int offset;
    private int limit;

    public TransactionHistoryRequest(String userId, String status, int offset, int limit) {
        this.userId = userId;
        this.status = status;
        this.offset = offset;
        this.limit = limit;
    }
}
