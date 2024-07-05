package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MobileRechargeBeneficiaryRepository extends JpaRepository<MobileRechargeBeneficiaryEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    List<MobileRechargeBeneficiaryEntity> findByUserId(String mobileRechargeBeneficiaryOid);

    @NotFound(action = NotFoundAction.IGNORE)
    Optional<MobileRechargeBeneficiaryEntity> findByUserIdAndBeneficiaryAccountNo(String userId, String beneficiaryAccountNo);

    @NotFound(action = NotFoundAction.IGNORE)
    Optional<MobileRechargeBeneficiaryEntity> findByMobileRechargeBeneficiaryOid(String mobileRechargeBeneficiaryOid);

    @NotFound(action = NotFoundAction.IGNORE)
    Optional<MobileRechargeBeneficiaryEntity> deleteByMobileRechargeBeneficiaryOid(String mobileRechargeBeneficiaryOid);

}
