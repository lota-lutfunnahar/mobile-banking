package net.celloscope.api.securityQuestion.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.celloscope.api.securityQuestion.domain.Question;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveSecurityQuestionCommand {

    private List<Question> questionAndAnswer;

    private String ibUserOid;

    private String status;


}
