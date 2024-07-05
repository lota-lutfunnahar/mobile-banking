package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadBeneficiaryList;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadBeneficiaryListAdapter implements LoadBeneficiaryList {

    private final MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository;

    public LoadBeneficiaryListAdapter(MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository) {
        this.mobileRechargeBeneficiaryRepository = mobileRechargeBeneficiaryRepository;
    }

    @Override
    public List<MobileRechargeBeneficiary> getBeneficiaryList(String userId) {
        List<MobileRechargeBeneficiaryEntity> beneficiaryEntities = mobileRechargeBeneficiaryRepository.findByUserId(userId);

        if (beneficiaryEntities == null || beneficiaryEntities.isEmpty()) return null;

        List<MobileRechargeBeneficiary> beneficiaries = new ArrayList<>();

        beneficiaryEntities.forEach(beneficiaryEntity -> {
            MobileRechargeBeneficiary mobileRechargeBeneficiary = mapToDomainEntity(beneficiaryEntity);
            beneficiaries.add(mobileRechargeBeneficiary);
        });

        return beneficiaries;

    }

    private MobileRechargeBeneficiary mapToDomainEntity(MobileRechargeBeneficiaryEntity mobileRechargeBeneficiaryEntity){
        return MobileRechargeBeneficiary.builder()
                .beneficiaryOid(mobileRechargeBeneficiaryEntity.getMobileRechargeBeneficiaryOid())
                .shortName(mobileRechargeBeneficiaryEntity.getShortName())
                .beneficiaryAccountNo(mobileRechargeBeneficiaryEntity.getBeneficiaryAccountNo())
                .operator(mobileRechargeBeneficiaryEntity.getOperator())
                .connectionType(mobileRechargeBeneficiaryEntity.getConnectionType())
                .currency(mobileRechargeBeneficiaryEntity.getCurrency())
                .build();
    }
}
