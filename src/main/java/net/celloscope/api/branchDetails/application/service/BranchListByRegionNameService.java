package net.celloscope.api.branchDetails.application.service;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.branchDetails.application.port.in.GetBranchesByRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.out.LoadBranchesByRegionNamePort;
import net.celloscope.api.branchDetails.domain.Branch;

import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BranchListByRegionNameService implements GetBranchesByRegionNameUseCase {
    private final LoadBranchesByRegionNamePort loadBranchesByRegionNamePort;

    @Override
    public List<Branch> getBranchesByRegionName(String regionName) throws ExceptionHandlerUtil {
        List<Branch> branches =  loadBranchesByRegionNamePort.loadBranchesByRegionName(regionName);
        return branches;
    }


}
