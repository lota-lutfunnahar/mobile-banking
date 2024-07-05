package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionHistoryListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface RobiRechargePendingHistoryUseCase {
    RobiTransactionHistoryListResponse getPendingResponse(TransactionHistoryRequest request);
}
