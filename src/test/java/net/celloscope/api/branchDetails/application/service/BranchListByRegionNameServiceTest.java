package net.celloscope.api.branchDetails.application.service;

import net.celloscope.api.branchDetails.adapter.out.persistence.BranchDetailsAdapter;
import net.celloscope.api.branchDetails.adapter.out.persistence.BranchDetailsEntity;
import net.celloscope.api.branchDetails.application.port.in.GetBranchesByRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.out.LoadBranchesByRegionNamePort;
import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchListByRegionNameServiceTest {

    //get all branches of a given region test
    @InjectMocks
    BranchListByRegionNameService GetBranchesByRegionNameUseCase;


    @Test
    public void shouldReturnTheBranchListWhenMethodGetBranchesByRegionNameIsCalled() throws ExceptionHandlerUtil {
        LoadBranchesByRegionNamePort persistenceOutPort = mock(LoadBranchesByRegionNamePort.class);
        GetBranchesByRegionNameUseCase cut = new BranchListByRegionNameService(persistenceOutPort);
        List<Branch> result = cut.getBranchesByRegionName("Dhaka Region");
        assertTrue(result.isEmpty());
    }
}