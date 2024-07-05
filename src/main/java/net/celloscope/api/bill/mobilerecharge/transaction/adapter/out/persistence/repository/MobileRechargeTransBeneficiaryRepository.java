package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransBeneficiaryEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileRechargeTransBeneficiaryRepository extends JpaRepository<MobileRechargeTransBeneficiaryEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    List<MobileRechargeTransBeneficiaryEntity>  findByBeneficiaryAccountNo(String beneficiaryAccountNo);
}
