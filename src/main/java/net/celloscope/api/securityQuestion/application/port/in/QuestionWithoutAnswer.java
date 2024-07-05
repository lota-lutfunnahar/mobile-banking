package net.celloscope.api.securityQuestion.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QuestionWithoutAnswer {

    @NotBlank(message = "key can not be null or empty")
    private String key;

    @NotBlank(message = "Question can not be null or empty")
    private String question;

}
