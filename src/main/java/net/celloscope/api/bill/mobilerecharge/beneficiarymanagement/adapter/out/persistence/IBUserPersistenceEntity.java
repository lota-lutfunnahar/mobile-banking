package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;
import net.celloscope.api.role.entity.RoleEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ibuser")
public class IBUserPersistenceEntity {
    @Id
    @Expose
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ibuseroid", updatable = false, nullable = false)
    private String ibUserOid;

    @Column(name="userid") @Expose
    private String userId;

    @Column(name="branchoid") @Expose
    private String branchOid;

    @Column(name="password") @Expose
    private String password;

    @Column(name="role") @Expose
    private String role;

    @Column(name="mobileno") @Expose
    private String mobileNo;

    @Column(name="status") @Expose
    private String status;

    @Column(name = "createdon") @Expose
    private Timestamp createdOn;

    @Column(name="editedby") @Expose
    private String editedBy;

    @Column(name="editedon") @Expose
    private Timestamp editedOn;

    @Column(name="currentversion") @Expose
    private String currentVersion;

    @Column(name="resetrequired") @Expose
    private String resetRequired;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "userrole",
            joinColumns = @JoinColumn(name = "ibuseroid", referencedColumnName = "ibuseroid") ,
            inverseJoinColumns = @JoinColumn(name = "roleoid", referencedColumnName = "roleoid")
    )
    private Collection<RoleEntity> roles;

    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this);
    }
}
