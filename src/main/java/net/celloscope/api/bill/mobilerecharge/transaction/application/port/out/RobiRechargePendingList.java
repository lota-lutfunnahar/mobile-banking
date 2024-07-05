package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;

import java.util.List;

public interface RobiRechargePendingList {
    List<TransactionHistory> get(String userId);
}
