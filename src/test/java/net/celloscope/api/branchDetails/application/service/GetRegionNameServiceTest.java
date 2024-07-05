package net.celloscope.api.branchDetails.application.service;

import net.celloscope.api.branchDetails.application.port.in.GetRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.out.LoadRegionNamePort;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRegionNameServiceTest {
    // get all regions name test
    @Test
    public void shouldReturnAllTheRegionsWhenMethodGetRegionNameIsCalled() throws ExceptionHandlerUtil {
        List<String> regions = Arrays.asList("Dhaka Region","Faridpur Region");
        LoadRegionNamePort persistenceOutPort = mock(LoadRegionNamePort.class);
        when(persistenceOutPort.loadRegionNamePort()).thenReturn(regions);
        GetRegionNameUseCase cut = new GetRegionNameService(persistenceOutPort);
        List<String> result = cut.getRegionName();
        System.out.println(result.size());
        assertFalse(result.isEmpty());
    }

}