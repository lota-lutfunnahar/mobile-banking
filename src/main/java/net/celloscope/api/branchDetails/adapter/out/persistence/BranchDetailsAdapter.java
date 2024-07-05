package net.celloscope.api.branchDetails.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.branchDetails.application.port.out.LoadBranchesByRegionNamePort;
import net.celloscope.api.branchDetails.application.port.out.LoadRegionNamePort;
import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.common.PersistenceAdapter;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@PersistenceAdapter
//@Transactional
public class BranchDetailsAdapter implements LoadBranchesByRegionNamePort, LoadRegionNamePort {

    ModelMapper modelMapper = new ModelMapper();
    private final BranchDetailsRepository branchDetailsRepository;

    @Override
    public List<Branch> loadBranchesByRegionName(String regionName) throws ExceptionHandlerUtil {
        List<BranchDetailsEntity> branchDetailEntity = branchDetailsRepository.findAllByRegionName(regionName);
        if(branchDetailEntity == null || branchDetailEntity.size() < 1){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Branch Details not found");
        }
        System.out.println(branchDetailEntity.size());
       // return
       return branchDetailEntity.stream().map(
                t-> modelMapper.map(t,  Branch.class)
        ).collect(Collectors.toList());
    }

    @Override
    public List<String> loadRegionNamePort() throws ExceptionHandlerUtil{
        List<String> regions = branchDetailsRepository.findAllRegionName();
        if(regions == null || regions.size() < 1){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Region list not found");
        }

        return regions;

//        return regions.stream().map(t -> {
//            return new RegionNames(t);
//        }).collect(Collectors.toList());
    }
}
