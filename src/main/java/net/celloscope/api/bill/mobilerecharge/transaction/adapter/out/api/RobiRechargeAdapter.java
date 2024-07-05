package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api.dto.RobiRechargePendingListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.RobiRechargePendingList;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;
import net.celloscope.api.core.client.MobileRechargeInterfaceClientImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RobiRechargeAdapter implements RobiRechargePendingList {
    private final MobileRechargeInterfaceClientImpl interfaceClient;
    private final ModelMapper modelMapper;
    private final String PENDING = "Pending";

    public RobiRechargeAdapter(MobileRechargeInterfaceClientImpl interfaceClient, ModelMapper modelMapper) {
        this.interfaceClient = interfaceClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TransactionHistory> get(String userId) {
        RobiRechargePendingListResponse pendingListResponse = interfaceClient.getRobiTransactionHistory(userId, PENDING);
        if (pendingListResponse.getData().isEmpty()) return new ArrayList<>();
        
        return pendingListResponse.getData().stream().map(response -> modelMapper.map(response, TransactionHistory.class)).collect(Collectors.toList());
    }
}
