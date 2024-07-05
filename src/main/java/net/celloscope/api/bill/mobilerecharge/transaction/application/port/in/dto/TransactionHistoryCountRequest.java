package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto;

import lombok.Getter;

@Getter
public class TransactionHistoryCountRequest {
    private String userId;
    private String bankAccountNo;
    private String status;

    public TransactionHistoryCountRequest(String userId, String bankAccountNo, String status) {
        this.userId = userId;
        this.bankAccountNo = bankAccountNo;
        this.status = status;
    }
}
