package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadMobileRechargeBeneficiary;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.MobileRechargeBeneficiary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoadMobileRechargeBeneficiaryAdapter implements LoadMobileRechargeBeneficiary {
    private final MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository;
    private final ModelMapper modelMapper;

    public LoadMobileRechargeBeneficiaryAdapter(MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository, ModelMapper modelMapper) {
        this.mobileRechargeBeneficiaryRepository = mobileRechargeBeneficiaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<MobileRechargeBeneficiary> getBeneficiary(String userId, String beneficiaryAccountNo) {
        Optional<MobileRechargeBeneficiaryEntity> beneficiary = mobileRechargeBeneficiaryRepository.findByUserIdAndBeneficiaryAccountNo(userId, beneficiaryAccountNo);

        return beneficiary.map(mobileRechargeBeneficiaryEntity -> modelMapper.map(mobileRechargeBeneficiaryEntity, MobileRechargeBeneficiary.class));

    }

    @Override
    public Optional<MobileRechargeBeneficiary> getBeneficiaryByOid(String beneficiaryOid) {
        Optional<MobileRechargeBeneficiaryEntity> beneficiary = mobileRechargeBeneficiaryRepository.findByMobileRechargeBeneficiaryOid(beneficiaryOid);

        return beneficiary.map(mobileRechargeBeneficiaryEntity -> modelMapper.map(mobileRechargeBeneficiaryEntity, MobileRechargeBeneficiary.class));
    }
}
