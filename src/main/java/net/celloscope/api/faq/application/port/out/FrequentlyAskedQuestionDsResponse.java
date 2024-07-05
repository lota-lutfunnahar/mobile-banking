package net.celloscope.api.faq.application.port.out;

import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;

import java.util.List;

@Data
@AllArgsConstructor
public class FrequentlyAskedQuestionDsResponse {
    private List<FrequentlyAskedQuestion> data;
    private int count;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
