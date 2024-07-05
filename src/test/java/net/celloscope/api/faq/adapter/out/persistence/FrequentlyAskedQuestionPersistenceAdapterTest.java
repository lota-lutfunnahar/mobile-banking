package net.celloscope.api.faq.adapter.out.persistence;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.FrequentlyAskedQuestionsResponse;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionCommand;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsResponse;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;
import net.celloscope.api.securityQuestion.adapter.out.persistence.SecurityQuestionPersistenceAdapter;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FrequentlyAskedQuestionPersistenceAdapterTest {

    @Mock
    FrequentlyAskedQuestionRepository frequentlyAskedQuestionRepository;

    @InjectMocks
    FrequentlyAskedQuestionPersistenceAdapter frequentlyAskedQuestionPersistenceAdapter;

    @Test
    void WhenValidRequestThenShouldReturnValidFrequentlyAskedQuestions() throws ExceptionHandlerUtil {
        Mockito.when(frequentlyAskedQuestionRepository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "sortOrder"))))
                .thenReturn(buildJpaResponse());
        FrequentlyAskedQuestionDsResponse response = frequentlyAskedQuestionPersistenceAdapter.loadFrequentlyAskedQuestions(buildFrequentlyAskedQuestionCommand());
        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(buildDsResponse());
    }

    @Test
    void WhenInvalidRequestThenShouldReturnEmptyFrequentlyAskedQuestions() throws ExceptionHandlerUtil {
        Mockito.when(frequentlyAskedQuestionRepository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "sortOrder"))))
                .thenReturn(buildEmptyJpaResponse());
        FrequentlyAskedQuestionDsResponse response = frequentlyAskedQuestionPersistenceAdapter.loadFrequentlyAskedQuestions(buildFrequentlyAskedQuestionCommand());
        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(buildEmptyDataDsResponse());
    }

    LoadFrequentlyAskedQuestionCommand buildFrequentlyAskedQuestionCommand() {
        return new LoadFrequentlyAskedQuestionCommand(0,10, "");
    }

    Page<FrequentlyAskedQuestionJpaEntity> buildJpaResponse(){
        FrequentlyAskedQuestionJpaEntity entity = FrequentlyAskedQuestionJpaEntity.builder()
                .frequentlyAskedQuestionOid("1234")
                .question("what is ibanking")
                .answer("internet banking")
                .sortOrder(1)
                .build();
        return new PageImpl<>(Arrays.asList(entity), PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "sortOrder")), 1);
    }

    Page<FrequentlyAskedQuestionJpaEntity> buildEmptyJpaResponse(){
        return new PageImpl<>(new ArrayList<>(), PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "sortOrder")), 0);
    }

    FrequentlyAskedQuestionDsResponse buildDsResponse(){
        return new FrequentlyAskedQuestionDsResponse(
                Arrays.asList(new FrequentlyAskedQuestion("1", "what is ibanking", "internet banking")),
                1);
    }

    FrequentlyAskedQuestionDsResponse buildEmptyDataDsResponse(){
        return new FrequentlyAskedQuestionDsResponse(new ArrayList<>(), 0);
    }
}