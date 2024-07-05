package net.celloscope.api.faq.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

public interface LoadFrequentlyAskedQuestionUseCase {
   FrequentlyAskedQuestionsResponse loadFrequentlyAskedQuestion(LoadFrequentlyAskedQuestionCommand request) throws ExceptionHandlerUtil;
}
