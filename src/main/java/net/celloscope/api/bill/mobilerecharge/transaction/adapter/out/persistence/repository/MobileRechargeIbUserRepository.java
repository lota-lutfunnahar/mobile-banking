package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeIbUserEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MobileRechargeIbUserRepository extends JpaRepository<MobileRechargeIbUserEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeIbUserEntity findByUserId(String userId);

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeIbUserEntity findByIbUserOid(String userId);

    @Query(value = "select username, userid, status from IbUser where userId = ?1 union" +
            " select username, userid, status from UnauthIbUser where userId = ?1 ", nativeQuery = true)
    String getUserByUserId(String userId);
}
