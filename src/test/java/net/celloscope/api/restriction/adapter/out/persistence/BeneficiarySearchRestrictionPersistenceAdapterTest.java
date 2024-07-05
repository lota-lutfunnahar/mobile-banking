package net.celloscope.api.restriction.adapter.out.persistence;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Utility;
import net.celloscope.api.restriction.application.service.RestrictUserBeneficiarySearchService;
import net.celloscope.api.securityQuestion.adapter.out.persistence.entity.SecurityQuestionJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BeneficiarySearchRestrictionPersistenceAdapterTest {

    @Mock
    BeneficiarySearchRestrictionRepository beneficiarySearchRestrictionRepository;

    @InjectMocks
    BeneficiarySearchRestrictionPersistenceAdapter beneficiarySearchRestrictionPersistenceAdapter;

    @Test
    void ShouldReturnDataIfValidRequest() throws ExceptionHandlerUtil {

        Mockito.when(beneficiarySearchRestrictionRepository.findById(any()))
                .thenReturn(Optional.of(buildData()));
        BeneficiarySearchRestriction beneficiarySearchRestriction = beneficiarySearchRestrictionPersistenceAdapter.get(any());
        Assertions.assertThat(beneficiarySearchRestriction)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(buildData());
    }

    @Test
    void ShouldReturnNullIfUserNotFound() throws ExceptionHandlerUtil {
        BeneficiarySearchRestriction beneficiarySearchRestriction = beneficiarySearchRestrictionPersistenceAdapter.get(any());
        Assertions.assertThat(beneficiarySearchRestriction)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(null);
    }

    private BeneficiarySearchRestriction buildData() {
        return new BeneficiarySearchRestriction("01717433473", null);
    }

}