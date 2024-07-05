package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IBUserDomain {
    private String ibUserOid;

    private String userId;

    private String branchOid;

    private String password;

    private String role;

    private String mobileNo;

    private String status;
}
