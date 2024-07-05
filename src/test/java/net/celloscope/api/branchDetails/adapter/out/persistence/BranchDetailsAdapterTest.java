package net.celloscope.api.branchDetails.adapter.out.persistence;

import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchDetailsAdapterTest {

    @Mock
    BranchDetailsRepository branchDetailsRepository;

    @InjectMocks
    BranchDetailsAdapter loadBranchesByRegionNamePort;
    @InjectMocks
    BranchDetailsAdapter LoadRegionNamePort;

    private  List<BranchDetailsEntity> branchDetailsEntities = Arrays.asList(
            new BranchDetailsEntity("sad56fd", "5sa55", "CHARIGRAM", "sfd", "54656", "34536", "DHAKA REGION"),
            new BranchDetailsEntity("sadsadsa56fd", "dfd5", "sg", "sfsad", "546cdsf56", "3453346", "LP"));

    @Test
    void shouldReturnBranchesWhenMethodLoadBranchesByRegionNameIsCalled() throws ExceptionHandlerUtil {
        when(branchDetailsRepository.findAllByRegionName("DHAKA REGION")).thenReturn(branchDetailsEntities);
        List<Branch> branchList = loadBranchesByRegionNamePort.loadBranchesByRegionName("DHAKA REGION");
        branchList.forEach(System.out::println);
        assertThat(branchList.get(0)).isEqualTo(buildBranch());
        System.out.println(branchList.get(0));
        System.out.println(buildBranch());
    }

  Branch buildBranch(){
        return new Branch("sad56fd", "5sa55", "CHARIGRAM", "sfd", "54656", "34536", "DHAKA REGION");
    }

    @Test
    void shouldReturnRegionNameWhenMethodLoadRegionNamePortIsCalled() throws ExceptionHandlerUtil {
        List<String> regions  = Arrays.asList("Dhaka Region","Faridpur Region");
        when(branchDetailsRepository.findAllRegionName()).thenReturn(regions);
        List<String> region= LoadRegionNamePort.loadRegionNamePort();
        region.forEach(System.out::println);
        assertThat(region.get(0)).isEqualTo(regions.get(0));
    }

}