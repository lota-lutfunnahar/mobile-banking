package net.celloscope.api.branchDetails.application.port.out;

import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.List;

public interface LoadBranchesByRegionNamePort {
    List<Branch> loadBranchesByRegionName(String regionName) throws ExceptionHandlerUtil;
    }