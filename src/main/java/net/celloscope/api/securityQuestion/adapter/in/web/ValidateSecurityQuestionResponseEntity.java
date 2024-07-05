package net.celloscope.api.securityQuestion.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateSecurityQuestionResponseEntity {
    private String userMessage;
}
