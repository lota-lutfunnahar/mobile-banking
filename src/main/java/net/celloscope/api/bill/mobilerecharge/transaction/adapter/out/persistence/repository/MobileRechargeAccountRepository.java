package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.AccountSingleEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeAccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransBeneficiaryEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileRechargeAccountRepository extends JpaRepository<AccountSingleEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    AccountSingleEntity findByAccountOid(String accountOid);

}
