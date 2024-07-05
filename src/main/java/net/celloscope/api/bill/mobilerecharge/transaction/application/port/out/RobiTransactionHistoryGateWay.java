package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryCountRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;

import java.util.List;

public interface RobiTransactionHistoryGateWay {
    List<TransactionHistory> getTransactionHistory(TransactionHistoryRequest request) ;

    int countTransactionHistory(TransactionHistoryCountRequest transactionHistoryCountRequest) ;
}
