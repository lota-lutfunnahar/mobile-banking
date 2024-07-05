package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import lombok.AllArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.AddMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AddMobileRechargeBeneficiaryAdapter implements AddMobileRechargeBeneficiary {

    private final MobileRechargeBeneficiaryRepository beneficiaryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<MobileRechargeBeneficiary> saveBeneficiary(MobileRechargeBeneficiary beneficiary) {
        modelMapper.typeMap(MobileRechargeBeneficiary.class, MobileRechargeBeneficiaryEntity.class).addMappings(
                mapper -> {
                    mapper.map(MobileRechargeBeneficiary::getUserId, MobileRechargeBeneficiaryEntity::setCreatedBy);
                }
        );
        MobileRechargeBeneficiaryEntity entity = beneficiaryRepository.save(modelMapper.map(beneficiary, MobileRechargeBeneficiaryEntity.class));
        return Optional.of(modelMapper.map(entity, MobileRechargeBeneficiary.class));
    }
}
