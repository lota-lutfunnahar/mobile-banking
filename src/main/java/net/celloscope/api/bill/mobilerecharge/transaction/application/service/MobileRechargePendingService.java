package net.celloscope.api.bill.mobilerecharge.transaction.application.service;


import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.RobiRechargePendingHistoryUseCase;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionHistoryListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryDto;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.RobiRechargePendingList;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.RobiTransactionHistoryGateWay;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;


import net.celloscope.api.core.util.Messages;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MobileRechargePendingService implements RobiRechargePendingHistoryUseCase {


    private final RobiTransactionHistoryGateWay robiTransactionHistoryDsGateway;
    private final RobiRechargePendingList robiRechargePendingList;
    private final ModelMapper modelMapper;

    public MobileRechargePendingService(RobiTransactionHistoryGateWay robiTransactionHistoryDsGateway, RobiRechargePendingList robiRechargePendingList, ModelMapper modelMapper) {
        this.robiTransactionHistoryDsGateway = robiTransactionHistoryDsGateway;
        this.robiRechargePendingList = robiRechargePendingList;
        this.modelMapper = modelMapper;

    }

    @Override
    public RobiTransactionHistoryListResponse getPendingResponse(TransactionHistoryRequest request) {

        List<TransactionHistory> transactionHistoryList = robiTransactionHistoryDsGateway.getTransactionHistory(request);


        List<TransactionHistory> responseList = robiRechargePendingList.get(request.getUserId());

        if (transactionHistoryList.isEmpty() || responseList.isEmpty()) return buildEmptyListResponse();

        log.info("Mobile Recharge pending data list: {}", responseList);

        List<TransactionHistory> filteredList = transactionHistoryList.stream().filter(transactionHistory -> responseList.stream().anyMatch(response -> response.getOriginatorConversationId().equalsIgnoreCase(transactionHistory.getOriginatorConversationId()))).collect(Collectors.toList());

        if (filteredList.isEmpty()) return buildEmptyListResponse();

        filteredList.forEach(transaction -> responseList.forEach(response -> {
            if (transaction.getOriginatorConversationId().equalsIgnoreCase(response.getOriginatorConversationId())){
                transaction.setStatus(response.getStatus());
                transaction.setTransactionDate(response.getTransactionDate());
            }
        }));

        return RobiTransactionHistoryListResponse.builder()
                .data(filteredList.stream().map(item -> modelMapper.map(item, TransactionHistoryDto.class)).collect(Collectors.toList()))
                .count(filteredList.size())
                .message(Messages.PENDING_LIST_RETRIVE_SUCCESSFUL)
                .build();
    }

    private RobiTransactionHistoryListResponse buildEmptyListResponse(){
        return RobiTransactionHistoryListResponse.builder()
                .data(new ArrayList<>())
                .count(0)
                .message(Messages.PENDING_LIST_EMPTY)
                .build();

    }
}
