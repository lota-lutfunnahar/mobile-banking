package net.celloscope.api.branchDetails.adapter.in.web;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.branchDetails.application.port.in.GetBranchesByRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.in.GetRegionNameUseCase;
import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.common.WebAdapter;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class BranchDetailsController {
    private final GetBranchesByRegionNameUseCase getBranchesByRegionName;
    private final GetRegionNameUseCase getRegionNameUseCase;

    @GetMapping(path = "/v1/bkb/branches")
    public ResponseEntity getBranches(
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestParam(name = "regionName", required = true) String regionName) throws Exception {
        try{
            List<Branch> branch = getBranchesByRegionName.getBranchesByRegionName(regionName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(branch);
        }catch (ExceptionHandlerUtil ex){
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }


    @GetMapping(path = "/v1/bkb/regions")
    public ResponseEntity<RegionNameResponseDTO> getRegionList(
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime) throws Exception {
            try{
                List<String> branchLocators = getRegionNameUseCase.getRegionName();
                RegionNameResponseDTO regionNameResponseDTO = RegionNameResponseDTO.builder().regionNames(branchLocators).build();
                return new  ResponseEntity<>(
                        regionNameResponseDTO, HttpStatus.OK);

            }catch (ExceptionHandlerUtil ex){
                throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
            }
    }

}
