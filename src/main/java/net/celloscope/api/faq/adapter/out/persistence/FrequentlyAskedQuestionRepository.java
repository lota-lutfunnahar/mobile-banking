package net.celloscope.api.faq.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrequentlyAskedQuestionRepository extends JpaRepository<FrequentlyAskedQuestionJpaEntity,String> {

    Page<FrequentlyAskedQuestionJpaEntity> findAll(Pageable pageable);
}
