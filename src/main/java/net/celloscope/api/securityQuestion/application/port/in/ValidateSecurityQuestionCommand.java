package net.celloscope.api.securityQuestion.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.celloscope.api.securityQuestion.domain.Question;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateSecurityQuestionCommand {
//    @NotBlank(message = "ibUserOid can not be null or empty")
    private String ibUserOid;

//    @NotBlank(message = "referenceOid can not be null or empty")
    private String referenceOid;

//    @NotBlank(message = "data can not be null or empty")
    private List<Question> data;
}
