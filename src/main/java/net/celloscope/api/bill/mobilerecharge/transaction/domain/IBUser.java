package net.celloscope.api.bill.mobilerecharge.transaction.domain;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;
import net.celloscope.api.role.entity.RoleEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IBUser {

    private String userId;

    private String ibUserOid;

    private String branchOid;

    private String password;

    private String role;

    private String mobileNo;

    private String status;

    private String resetRequired;

    private Collection<RoleEntity> roles;

    @Override
    public String toString(){
    return new GsonBuilder().setPrettyPrinting().create().toJson(this);
}
}
