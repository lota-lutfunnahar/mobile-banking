package net.celloscope.api.securityQuestion.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadSecurityQuestonResponseEntity {

    private List<QuestionWithoutAnswer> data;

    private String userMessage;
}
