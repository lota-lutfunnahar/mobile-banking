package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MobileRechargeCallBackServiceTest {

    @Autowired
    MobileRechargeCallBackService mobileRechargeCallBackService;


    @Test
    void contextLoad(){
        assertThat(mobileRechargeCallBackService).isNotNull();
    }

}