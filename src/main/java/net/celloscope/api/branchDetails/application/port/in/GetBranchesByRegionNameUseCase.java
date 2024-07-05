package net.celloscope.api.branchDetails.application.port.in;

import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.List;

public interface GetBranchesByRegionNameUseCase {
    List<Branch> getBranchesByRegionName(String regionName) throws ExceptionHandlerUtil;
}
