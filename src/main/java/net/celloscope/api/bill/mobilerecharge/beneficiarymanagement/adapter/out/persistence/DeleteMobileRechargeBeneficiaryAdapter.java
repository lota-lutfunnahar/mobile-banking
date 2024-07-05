package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.DeleteMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class DeleteMobileRechargeBeneficiaryAdapter implements DeleteMobileRechargeBeneficiary {
    private final MobileRechargeBeneficiaryRepository beneficiaryRepository;
    private final ModelMapper modelMapper;

    public DeleteMobileRechargeBeneficiaryAdapter(MobileRechargeBeneficiaryRepository beneficiaryRepository, ModelMapper modelMapper) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<MobileRechargeBeneficiary> delete(String beneficiaryOid) {
        Optional<MobileRechargeBeneficiaryEntity> deletableEntity = beneficiaryRepository.findByMobileRechargeBeneficiaryOid(beneficiaryOid);

        if (!deletableEntity.isPresent()) return Optional.empty();

        try{
            beneficiaryRepository.deleteById(beneficiaryOid);

        } catch (IllegalArgumentException ex){

            log.error(ex.getMessage(), ex);
            return Optional.empty();

        }

        return Optional.of( modelMapper.map(deletableEntity.get(), MobileRechargeBeneficiary.class) );
    }
}
