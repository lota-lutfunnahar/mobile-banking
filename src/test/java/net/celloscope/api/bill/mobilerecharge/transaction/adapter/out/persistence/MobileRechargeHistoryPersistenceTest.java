package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.dto.TransactionHistoryRequest;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MobileRechargeHistoryPersistenceTest {


    @Autowired
    private MobileRechargeHistoryPersistence mobileRechargeHistoryPersistence;


    @Test
    void contextLoad(){
        Assertions.assertThat(mobileRechargeHistoryPersistence).isNotNull();
    }

    @Test
    void givenParam_returnTransactionList(){
        TransactionHistoryRequest request = new TransactionHistoryRequest("01737711756", "", 0, 10);
        List<TransactionHistory> transactionHistories =  mobileRechargeHistoryPersistence.getTransactionHistory(request);
        System.out.println(transactionHistories);
        Assertions.assertThat(transactionHistories).isNotNull();

    }

}