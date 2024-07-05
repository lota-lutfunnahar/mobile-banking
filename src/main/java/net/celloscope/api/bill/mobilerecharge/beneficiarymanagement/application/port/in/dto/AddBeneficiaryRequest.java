package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddBeneficiaryRequest {
    @NotNull(message = "shortName can't be NULL")
    @NotEmpty(message = "shortName can't be empty")
    private String shortName;
    private String description;

    @NotNull(message = "beneficiaryAccountNo can't be NULL")
    @NotEmpty(message = "beneficiaryAccountNo can't be empty")
    @Pattern(regexp = "^01[3-9]\\d{8}$", message = "beneficiaryAccountNo no is not valid")
    private String beneficiaryAccountNo; //mobileNo of beneficiary

    @NotNull(message = "currency can't be NULL")
    @NotEmpty(message = "currency can't be empty")
    private String currency;

    @NotNull(message = "operator can't be NULL")
    @NotEmpty(message = "operator can't be empty")
    private String operator;

    @NotNull(message = "connectionType can't be NULL")
    @NotEmpty(message = "connectionType can't be empty")
    private String connectionType;
}
