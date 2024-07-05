package net.celloscope.api.changePasswordWithOTP.application.port.in;

import com.google.gson.GsonBuilder;
import lombok.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReSendOTPCommand {
    @NotNull(message = "RefId should not be empty")
    private String refId;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
