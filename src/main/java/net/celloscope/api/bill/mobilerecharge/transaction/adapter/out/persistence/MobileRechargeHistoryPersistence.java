package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository.TransactionHistoryMobileRepository;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryCountRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.RobiTransactionHistoryGateWay;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MobileRechargeHistoryPersistence implements RobiTransactionHistoryGateWay {

    private final TransactionHistoryMobileRepository transactionHistoryCustomRepository;

    public MobileRechargeHistoryPersistence(TransactionHistoryMobileRepository transactionHistoryCustomRepository) {
        this.transactionHistoryCustomRepository = transactionHistoryCustomRepository;
    }

    @Override
    public List<TransactionHistory> getTransactionHistory(TransactionHistoryRequest request) {
        return transactionHistoryCustomRepository.getTransactionHistoryList(request.getUserId(),
                request.getStatus(), request.getOffset(), request.getLimit());
    }

    @Override
    public int countTransactionHistory(TransactionHistoryCountRequest transactionHistoryCountRequest) {
        return 0;
    }
}
