package net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.repository;

import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.entity.ChangePasswordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangePasswordRepository extends JpaRepository<ChangePasswordJpaEntity,String> {
    Optional<ChangePasswordJpaEntity> findByChangePasswordOid(String changePasswordOid);
    Optional<ChangePasswordJpaEntity> findByRefId(String refId);
    Optional<ChangePasswordJpaEntity> findByIbUserOid(String ibUserOid);

}
