package net.celloscope.api.restriction.application.port.in;

import net.celloscope.api.core.util.Utility;
import net.celloscope.api.restriction.adapter.out.persistence.BeneficiarySearchRestriction;
import net.celloscope.api.restriction.application.port.out.LoadUserRestrictionPort;
import net.celloscope.api.restriction.application.port.out.SaveUserRestrictionPort;
import net.celloscope.api.restriction.application.service.RestrictUserBeneficiarySearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateUserRestrictionOfBeneficiarySearchUseCaseTest {

    @Mock
    LoadUserRestrictionPort loadUserRestrictionPort;

    @Mock
    SaveUserRestrictionPort saveUserRestrictionPort;

    @Mock
    Utility utility;

    @InjectMocks
    RestrictUserBeneficiarySearchService restrictUserBeneficiarySearchService;

    @Test
    void contextLoading() {
        assertNotNull(RestrictUserBeneficiarySearchService.class);
    }

    @Test
    void allowUserToSearchBeneficiaryWhenUserRequestsForTheFirstTimeOn0minAndIfThereIsNoPreviousRequest(){
        when(loadUserRestrictionPort.get(any())).thenReturn(null);
        Assertions.assertFalse(restrictUserBeneficiarySearchService.checkIfUserIsRestrictedForBeneficiarySearch("01717433473"));
    }

    @Test
    void allowUserToSearchBeneficiaryWhenUserRequestForSecondTimeOn3minAndIfThereIsOnePreviousRequest(){
        when(loadUserRestrictionPort.get(any())).thenReturn(buildData());
        Assertions.assertFalse(restrictUserBeneficiarySearchService.checkIfUserIsRestrictedForBeneficiarySearch("01717433473"));
    }

    @Test
    void allowUserToSearchBeneficiaryWhenUserRequestForThirdTimeOn5minAndIfThereIsTwoPreviousRequest(){
        when(loadUserRestrictionPort.get(any())).thenReturn(buildSecondData());
        Assertions.assertFalse(restrictUserBeneficiarySearchService.checkIfUserIsRestrictedForBeneficiarySearch("01717433473"));
    }


    @Test
    void DoNotAllowUserToSearchBeneficiaryWhenUserRequestForFourthTimeInConsecutive5mins(){
        when(loadUserRestrictionPort.get(any())).thenReturn(buildThirdData());
        Assertions.assertTrue(restrictUserBeneficiarySearchService.checkIfUserIsRestrictedForBeneficiarySearch("01717433473"));
    }

    BeneficiarySearchRestriction buildData(){
        ConcurrentLinkedQueue<Date> queueDates = new ConcurrentLinkedQueue<Date>();
        queueDates.add(new Date());
        return new BeneficiarySearchRestriction("01717433473", queueDates);
    }

    BeneficiarySearchRestriction buildSecondData(){
        ConcurrentLinkedQueue<Date> queueDates = new ConcurrentLinkedQueue<Date>();
        queueDates.add(new Date());
        queueDates.add(new Date());
        return new BeneficiarySearchRestriction("01717433473", queueDates);
    }

    BeneficiarySearchRestriction buildThirdData(){
        ConcurrentLinkedQueue<Date> queueDates = new ConcurrentLinkedQueue<Date>();
        queueDates.add(new Date());
        queueDates.add(new Date());
        queueDates.add(new Date());
        return new BeneficiarySearchRestriction("01717433473", queueDates);
    }
}