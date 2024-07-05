package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbConsumerEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRechargeIbConsumerRepository extends JpaRepository<MobileRechargeIbConsumerEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeIbConsumerEntity findByIbUserOid(String ibUserOid);
}
