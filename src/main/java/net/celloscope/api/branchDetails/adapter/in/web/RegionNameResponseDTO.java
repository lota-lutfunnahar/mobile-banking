package net.celloscope.api.branchDetails.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionNameResponseDTO {
    List<String> regionNames;
}
