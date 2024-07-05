package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IbConsumer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MobileRechargeInfoPersistenceTest {

    @Autowired
    private MobileRechargeInfoPersistence mobileRechargeInfoPersistence;

    @Test
    void contextLoad(){
        Assertions.assertThat(mobileRechargeInfoPersistence).isNotNull();
    }

    /*@Test
    void givenUserOid_returnsIbConsumer() throws Exception{
        //given
        String userOid = "9f4ebb1c-7f3a-4030-aa51-6727265dcfc7";
        //then
        MobileRechargeIbConsumerEntity consumer = mobileRechargeInfoPersistence.findByIbUserOid(userOid);
        Assertions.assertThat(consumer).isNotNull();
        Assertions.assertThat(consumer.getCustomers()).isNotNull();
    }*/


    /*@Test
    void givenAccountOid_returnsAccount() throws Exception{
        //given
        String accountOid = "ae256741-d412-4f55-aa99-ebf45248c163";
        //then
        Account consumer = mobileRechargeInfoPersistence.findAccountInfo(accountOid);
        Assertions.assertThat(consumer).isNotNull();
    }*/

}