package net.celloscope.api.changePasswordWithOTP.application.port.in;

import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOTPCommand {

    @NotNull(message = "OTP can not be empty")
    private String otp;
    @NotNull(message = "refId can not be empty")
    private String refId;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
