package net.celloscope.api.faq.application.port.in;

import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;

import java.util.List;

@Data
@AllArgsConstructor
public class FrequentlyAskedQuestionsResponse {
    private List<FrequentlyAskedQuestion> data;
    private int count;
    private String userMessage;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
