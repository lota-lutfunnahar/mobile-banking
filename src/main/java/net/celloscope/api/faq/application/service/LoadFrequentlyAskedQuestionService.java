package net.celloscope.api.faq.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.FrequentlyAskedQuestionsResponse;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionUseCase;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionCommand;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsGateway;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsResponse;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static net.celloscope.api.core.util.Messages.Yes;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadFrequentlyAskedQuestionService implements LoadFrequentlyAskedQuestionUseCase {

    private final FrequentlyAskedQuestionDsGateway frequentlyAskedQuestionDsGateway;

    @Override
    public FrequentlyAskedQuestionsResponse loadFrequentlyAskedQuestion(LoadFrequentlyAskedQuestionCommand request) throws ExceptionHandlerUtil {
        FrequentlyAskedQuestionDsResponse dsResponse = frequentlyAskedQuestionDsGateway.loadFrequentlyAskedQuestions(request);
        if(dsResponse.getCount() == 0) throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "FAQ list not found");
        return new FrequentlyAskedQuestionsResponse(dsResponse.getData(),
                dsResponse.getCount(),
                "FAQ list retrieved successfully");
    }
}
