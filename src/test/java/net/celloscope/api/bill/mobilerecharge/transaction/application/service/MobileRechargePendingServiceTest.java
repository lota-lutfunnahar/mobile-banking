package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api.dto.RobiRechargePendingListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api.dto.RobiRechargePendingResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.RobiTransactionHistoryListResponse;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.core.client.MobileRechargeInterfaceClientImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MobileRechargePendingServiceTest {
    @Autowired
    MobileRechargePendingService pendingService;
    @MockBean
    MobileRechargeInterfaceClientImpl interfaceClient;

    @Test
    void contextLoad(){
        assertThat(pendingService).isNotNull();
    }

    /*@Test
    void givenTransactionHistoryRequest_returnsRobiTransactionHistoryListResponse(){
        //given
        TransactionHistoryRequest request = new TransactionHistoryRequest("01737711756", "Pending", 0, 100);
        //when
        when(interfaceClient.getRobiTransactionHistory(any(), any())).thenReturn(buildRobiRechargePendingListResponse());
        //then
        RobiTransactionHistoryListResponse response = pendingService.getPendingResponse(request);
        System.out.println("::::::::::::" + response.toString());

        assertThat(response.getData().isEmpty()).isFalse();

    }*/

    private RobiRechargePendingListResponse buildRobiRechargePendingListResponse(){
        List<RobiRechargePendingResponse> responses = new ArrayList<>();
        String date = new Date().toString();
            responses.add(RobiRechargePendingResponse.builder()
                            .originatorConversationId("3ded7639-3f5d-4aa9-bdc0-670273753a30")
                            .transactionDate(date)
                            .status("Pending")
                    .build());
            responses.add(RobiRechargePendingResponse.builder()
                            .originatorConversationId("6990c677-3d4d-4109-bfc0-5f090d7f61b3")
                            .transactionDate(date)
                            .status("Pending")
                    .build());


        return RobiRechargePendingListResponse.builder().data(responses).build();

    }

}