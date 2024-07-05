package net.celloscope.api.branchDetails.adapter.out.persistence;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "branch")

public class BranchDetailsEntity {

    @Id
    @Column(name="branchoid")
    private String branchOid;

    @Column(name="branchcode")
    private String branchCode;

    @Column(name="branchname")
    private String branchName;

    @Column(name="address")
    private String address;

    @Column(name="telephoneno")
    private String mobileNo;

    @Column(name="telephoneno2")
    private String phoneNo;

    @Column(name="regionname")
    private String regionName;


    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
