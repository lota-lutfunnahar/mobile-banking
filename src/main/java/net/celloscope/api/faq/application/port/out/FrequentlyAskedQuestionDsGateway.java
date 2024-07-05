package net.celloscope.api.faq.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionCommand;

public interface FrequentlyAskedQuestionDsGateway {
    FrequentlyAskedQuestionDsResponse loadFrequentlyAskedQuestions(LoadFrequentlyAskedQuestionCommand request) throws ExceptionHandlerUtil;
}
