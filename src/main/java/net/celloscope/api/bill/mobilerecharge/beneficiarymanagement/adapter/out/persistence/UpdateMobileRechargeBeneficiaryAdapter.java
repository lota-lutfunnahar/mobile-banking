package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.UpdateMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class UpdateMobileRechargeBeneficiaryAdapter implements UpdateMobileRechargeBeneficiary {
    private final MobileRechargeBeneficiaryRepository beneficiaryRepository;
    private final ModelMapper modelMapper;

    public UpdateMobileRechargeBeneficiaryAdapter(MobileRechargeBeneficiaryRepository beneficiaryRepository, ModelMapper modelMapper) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<MobileRechargeBeneficiary> update(MobileRechargeBeneficiary beneficiary) {
        modelMapper.typeMap(MobileRechargeBeneficiary.class, MobileRechargeBeneficiaryEntity.class).addMappings(mapper -> {
            mapper.map(MobileRechargeBeneficiary::getUserId, MobileRechargeBeneficiaryEntity::setCreatedBy);
            mapper.map(MobileRechargeBeneficiary::getUserId, MobileRechargeBeneficiaryEntity::setEditedBy);
        });
        Optional<MobileRechargeBeneficiaryEntity> existedEntity = beneficiaryRepository.findByMobileRechargeBeneficiaryOid(beneficiary.getBeneficiaryOid());

        if (!existedEntity.isPresent()) return Optional.empty();

        beneficiary.setUserId(existedEntity.get().getUserId());
        beneficiary.setCurrency(existedEntity.get().getCurrency());

        MobileRechargeBeneficiaryEntity saveAbleEntity = modelMapper.map(beneficiary, MobileRechargeBeneficiaryEntity.class);
        saveAbleEntity.setEditedOn(new Date());

        MobileRechargeBeneficiaryEntity savedEntity = beneficiaryRepository.save(saveAbleEntity);

        return Optional.of(modelMapper.map(savedEntity, MobileRechargeBeneficiary.class));
    }
}
