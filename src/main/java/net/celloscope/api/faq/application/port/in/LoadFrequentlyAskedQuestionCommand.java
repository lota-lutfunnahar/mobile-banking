package net.celloscope.api.faq.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;

import java.util.List;

@AllArgsConstructor
@Getter
public class LoadFrequentlyAskedQuestionCommand {
    private int offset;
    private int limit;
    private String searchText;
}
