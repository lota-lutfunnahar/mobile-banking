package net.celloscope.api.changePasswordWithOTP.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.entity.ChangePasswordJpaEntity;
import net.celloscope.api.changePasswordWithOTP.adapter.out.persistance.repository.ChangePasswordRepository;
import net.celloscope.api.changePasswordWithOTP.application.port.out.LoadChangePasswordPort;
import net.celloscope.api.changePasswordWithOTP.application.port.out.SaveChangePasswordPort;
import net.celloscope.api.changePasswordWithOTP.application.port.out.UpdateChangePasswordPort;
import net.celloscope.api.changePasswordWithOTP.domain.ChangePassword;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.securityQuestion.domain.SecurityQuestion;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChangePasswordPersistenceAdapter implements LoadChangePasswordPort, SaveChangePasswordPort, UpdateChangePasswordPort {

    private final ChangePasswordRepository changePasswordRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ChangePassword loadChangePasswordForUserByChangePassOid(String changePassOid) throws ExceptionHandlerUtil {
        ChangePasswordJpaEntity changePasswordEntity = changePasswordRepository.findByChangePasswordOid(changePassOid)
                .orElseThrow(() -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "No ChangePasswordJpaEntity entity found"));
      return modelMapper.map(changePasswordEntity,ChangePassword.class);

    }

    @Override
    public ChangePassword loadChangePasswordForUserByRefId(String refId) throws ExceptionHandlerUtil {
        ChangePasswordJpaEntity changePasswordEntity = changePasswordRepository.findByRefId(refId)
                .orElseThrow(() -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "No ChangePasswordJpaEntity entity found"));
        return modelMapper.map(changePasswordEntity,ChangePassword.class);
    }

    @Override
    public boolean saveChangePasswordForUser(ChangePassword changePassword) throws ExceptionHandlerUtil {
        ChangePasswordJpaEntity changePasswordJpaEntity = modelMapper.map(changePassword, ChangePasswordJpaEntity.class);
        changePasswordJpaEntity.setCreatedOn(new Date());
        ChangePasswordJpaEntity savedChangePasswordJpa = changePasswordRepository.save(changePasswordJpaEntity);
        if (savedChangePasswordJpa.getChangePasswordOid().isEmpty()){
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to Save ChangePasswordJpaEntity");
        }
        return true;
    }

    @Override
    public boolean updateChangePassword(ChangePassword changePassword) throws ExceptionHandlerUtil {
        ChangePasswordJpaEntity existChangePasswordJpaEntity = changePasswordRepository.findByRefId(changePassword.getRefId())
                .orElseThrow(() -> new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, " No ChangePasswordJpaEntity entity found"));

        modelMapper.typeMap(ChangePassword.class, ChangePasswordJpaEntity.class)
                        .addMappings( mapping -> {
                            mapping.skip(ChangePasswordJpaEntity::setChangePasswordOid);
                        });

        modelMapper.map(changePassword, existChangePasswordJpaEntity);
        existChangePasswordJpaEntity.setEditedBy(changePassword.getIbUserOid());
        existChangePasswordJpaEntity.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        ChangePasswordJpaEntity updatedPasswordJpaEntity = changePasswordRepository.save(existChangePasswordJpaEntity);
        if (updatedPasswordJpaEntity.getChangePasswordOid().isEmpty()){
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST,"Failed to Update ChangePasswordJpaEntity");
        }
        return true;
    }
}
