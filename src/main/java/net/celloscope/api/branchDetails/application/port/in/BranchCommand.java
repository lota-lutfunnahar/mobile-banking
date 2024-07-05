package net.celloscope.api.branchDetails.application.port.in;

import lombok.Value;

@Value
public class BranchCommand {
    private String branchOid;
    private String branchCode;
    private String branchName;
    private String address;
    private String mobileNo;
    private String phoneNo;
    private String regionName;
}
