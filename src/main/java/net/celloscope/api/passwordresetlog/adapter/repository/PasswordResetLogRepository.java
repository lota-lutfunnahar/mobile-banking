package net.celloscope.api.passwordresetlog.adapter.repository;

import net.celloscope.api.passwordresetlog.adapter.entity.PasswordResetLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordResetLogRepository extends JpaRepository<PasswordResetLogEntity, String> {
    List<PasswordResetLogEntity> findAllByUserIdOrderByCreatedOnDesc(String userId);
}
