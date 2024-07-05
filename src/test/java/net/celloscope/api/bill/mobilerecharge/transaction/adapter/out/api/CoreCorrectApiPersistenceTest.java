package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreCorrectApiPersistenceTest {

    @Autowired
    private CoreCorrectApiPersistence coreCorrectApiPersistence;


    @Test
    void contextLoad(){
        Assertions.assertThat(coreCorrectApiPersistence).isNotNull();
    }

}