package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product")
public class MobileRechargeProductEntity {

    @Id
    @Column(name="productoid")
    private String productOid;

    @Column(name="productid")
    private String productId;

    @Column(name="producttype")
    private String productType;

    @Column(name="productname")
    private String productName;

    @Column(name="legacyglcode")
    private String legacyGlCode;

    @Column(name="productdefinition")
    private String productDefinition;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}