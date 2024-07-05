package net.celloscope.api.restriction.application.service;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.core.util.Utility;
import net.celloscope.api.restriction.adapter.out.persistence.BeneficiarySearchRestriction;
import net.celloscope.api.restriction.application.port.in.ValidateUserRestrictionOfBeneficiarySearchUseCase;
import net.celloscope.api.restriction.application.port.out.LoadUserRestrictionPort;
import net.celloscope.api.restriction.application.port.out.SaveUserRestrictionPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class RestrictUserBeneficiarySearchService implements ValidateUserRestrictionOfBeneficiarySearchUseCase {

    private final LoadUserRestrictionPort loadUserRestrictionPort;
    private final SaveUserRestrictionPort saveUserRestrictionPort;
    private final Utility utility;

    @Value("${beneficiary.search.allowed.count}")
    private int allowedCount = 3;

    @Value("${beneficiary.search.allowed.timeframe.in.minutes}")
    private int allowedTimeFrameInMins = 5;

    @Override
    public boolean checkIfUserIsRestrictedForBeneficiarySearch(String userId) {

        BeneficiarySearchRestriction beneficiarySearchRestriction = loadUserRestrictionPort.get(userId);

        if(beneficiarySearchRestriction == null){
            insertFirstDataInStore(userId);
            return false;
        }
        //check if user exceeded allowed count
        ConcurrentLinkedQueue<Date> queuedLastDateTimeList = beneficiarySearchRestriction.getDates();
        if(!queuedLastDateTimeList.isEmpty() && queuedLastDateTimeList.size() < allowedCount)
        {
            updateDateInStore(queuedLastDateTimeList, beneficiarySearchRestriction);
            return false;
        }
        //check if user exceeded allowed count in allowed time frame
        Date earliest = queuedLastDateTimeList.peek();
        if(utility.getDateDiffInMins(earliest, new Date()) <= allowedTimeFrameInMins){
            return true;
        }
        //remove first data and insert new time
        queuedLastDateTimeList.poll();
        updateDateInStore(queuedLastDateTimeList, beneficiarySearchRestriction);
        return false;
    }

    void insertFirstDataInStore(String userId){
        ConcurrentLinkedQueue<Date> queue = new ConcurrentLinkedQueue<Date>();
        queue.add(new Date());
        saveUserRestrictionPort.save(new BeneficiarySearchRestriction(userId, queue));
    }

    void updateDateInStore(ConcurrentLinkedQueue<Date> queuedDateTimeList, BeneficiarySearchRestriction entity ){
        queuedDateTimeList.add(new Date());
        saveUserRestrictionPort.save(entity);
    }
}

