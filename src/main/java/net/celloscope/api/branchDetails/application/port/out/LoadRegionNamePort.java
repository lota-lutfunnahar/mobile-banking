package net.celloscope.api.branchDetails.application.port.out;

import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.List;

public interface LoadRegionNamePort {
    List<String> loadRegionNamePort() throws ExceptionHandlerUtil;
}
