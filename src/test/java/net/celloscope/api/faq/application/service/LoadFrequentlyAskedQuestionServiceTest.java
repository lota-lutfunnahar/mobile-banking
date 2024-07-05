package net.celloscope.api.faq.application.service;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.FrequentlyAskedQuestionsResponse;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionCommand;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsGateway;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsResponse;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LoadFrequentlyAskedQuestionServiceTest {

    @Mock
    FrequentlyAskedQuestionDsGateway frequentlyAskedQuestionDsGateway;

    @InjectMocks
    LoadFrequentlyAskedQuestionService loadFrequentlyAskedQuestionUseCase;


    @Test
    void loadFrequentlyAskedQuestionsForUser() throws ExceptionHandlerUtil {
        Mockito.when(frequentlyAskedQuestionDsGateway.loadFrequentlyAskedQuestions(any()))
                .thenReturn(buildDsResponse());
        FrequentlyAskedQuestionsResponse response = loadFrequentlyAskedQuestionUseCase.loadFrequentlyAskedQuestion(buildFrequentlyAskedQuestionCommand());

        org.assertj.core.api.Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(buildServiceResponse());
    }

    @Test
    void loadSecurityQuestionForUser() throws ExceptionHandlerUtil {
        Mockito.when(frequentlyAskedQuestionDsGateway.loadFrequentlyAskedQuestions(any()))
                .thenReturn(buildFailedDsResponse());
        Throwable thrown = assertThrows(ExceptionHandlerUtil.class, () -> {
            loadFrequentlyAskedQuestionUseCase.loadFrequentlyAskedQuestion(any());
        });
        assertEquals("FAQ list not found", thrown.getMessage());
    }


    LoadFrequentlyAskedQuestionCommand buildFrequentlyAskedQuestionCommand() {
        return new LoadFrequentlyAskedQuestionCommand(0,10, "");
    }

    FrequentlyAskedQuestionDsResponse buildDsResponse(){
        return new FrequentlyAskedQuestionDsResponse(
                Arrays.asList(new FrequentlyAskedQuestion("1", "why ibanking", "cause you need it")),
                1);
    }

    FrequentlyAskedQuestionDsResponse buildFailedDsResponse(){
        return new FrequentlyAskedQuestionDsResponse(
                new ArrayList<>(),
                0);
    }

    FrequentlyAskedQuestionsResponse buildServiceResponse(){
        return new FrequentlyAskedQuestionsResponse(
                Arrays.asList(new FrequentlyAskedQuestion("1", "why ibanking", "cause you need it")),
                1, "FAQ list retrieved successfully");
    }
}