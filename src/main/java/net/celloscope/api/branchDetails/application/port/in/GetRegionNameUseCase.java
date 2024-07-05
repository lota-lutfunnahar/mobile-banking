package net.celloscope.api.branchDetails.application.port.in;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.List;

public interface GetRegionNameUseCase {
    List<String> getRegionName() throws ExceptionHandlerUtil;
}
