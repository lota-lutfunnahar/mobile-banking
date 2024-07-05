package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeProductEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRechargeProductRepository extends JpaRepository<MobileRechargeProductEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeProductEntity findByProductOid(String productOid);

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeProductEntity findByProductId(String productId);

}