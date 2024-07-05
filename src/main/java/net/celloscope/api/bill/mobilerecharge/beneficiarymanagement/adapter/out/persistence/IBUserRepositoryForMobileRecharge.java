package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBUserRepositoryForMobileRecharge extends JpaRepository<IBUserPersistenceEntity, String> {
    Optional<IBUserPersistenceEntity> findByUserId(String userId);
}
