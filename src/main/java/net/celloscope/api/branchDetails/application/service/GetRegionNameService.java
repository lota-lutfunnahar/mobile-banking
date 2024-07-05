package net.celloscope.api.branchDetails.application.service;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.branchDetails.application.port.in.GetRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.out.LoadRegionNamePort;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GetRegionNameService implements GetRegionNameUseCase {
    private final LoadRegionNamePort loadRegionNamePort;


    @Override
    public List<String> getRegionName() throws ExceptionHandlerUtil {
        List<String> regions =  loadRegionNamePort.loadRegionNamePort();
        return regions;
    }

}
