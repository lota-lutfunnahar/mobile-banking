package net.celloscope.api.faq.adapter.out.persistence;

import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FrequentlyAskedQuestionRepositoryTest {
    @Autowired
    FrequentlyAskedQuestionRepository repository;

    @Test
    void findFaqList() {
        repository.save(FrequentlyAskedQuestionJpaEntity.builder()
                .question("whats ibanking")
                .answer("internet banking")
                .sortOrder(1)
                .build());
        Page<FrequentlyAskedQuestionJpaEntity> entities = repository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "sortOrder")));
        assertNotNull(entities);
        assertEquals(1, entities.getTotalElements());
    }
}