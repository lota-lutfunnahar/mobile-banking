package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LoadIBUser;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.domain.IBUserDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoadIBUserAdapter implements LoadIBUser {

    private final IBUserRepositoryForMobileRecharge IBUserRepository;
    private final ModelMapper modelMapper;

    public LoadIBUserAdapter(IBUserRepositoryForMobileRecharge IBUserRepository, ModelMapper modelMapper) {
        this.IBUserRepository = IBUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<IBUserDomain> getIBUser(String userId) {
        Optional<IBUserPersistenceEntity> userEntity = IBUserRepository.findByUserId(userId);

        return userEntity.map(ibUserPersistenceEntity -> modelMapper.map(ibUserPersistenceEntity, IBUserDomain.class));
    }
}
