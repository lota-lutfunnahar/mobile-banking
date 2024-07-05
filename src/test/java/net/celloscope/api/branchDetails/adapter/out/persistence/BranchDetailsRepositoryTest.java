package net.celloscope.api.branchDetails.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BranchDetailsRepositoryTest {

    @Autowired
    BranchDetailsRepository branchDetailsRepository;

    @Test
    public void shouldReturnTheListofRegionsWhenRepositoryIsUsed() {
        branchDetailsRepository.saveAll(buildBranchEntityList());
        List<String> allRegionName = branchDetailsRepository.findAllRegionName();
//        allRegionName.forEach(System.out::println);
//        System.out.println(allRegionName.size());
        assertThat(allRegionName).isNotEmpty();
    }

    @Test
    public void shouldReturnBranchWhenRepositoryIsUsedToSearchBranchesWithinARegion() {

        List<BranchDetailsEntity> branchDetailsEntities = Arrays.asList(
                new BranchDetailsEntity("sad56fd", "5sa55", "CHARIGRAM", "sfd", "54656", "34536", "DHAKA REGION"),
                new BranchDetailsEntity("sadsadsa56fd", "dfd5", "sg", "sfsad", "546cdsf56", "3453346", "DHAKA REGION"),
                new BranchDetailsEntity("safadfadfdfds", "dfd5", "sg", "sfsad", "546cdsf56", "3453346", "BHOLA REGION"));

        branchDetailsRepository.saveAll(branchDetailsEntities);
//        List<BranchDetailsEntity> repositoryAll = branchDetailsRepository.findAll();

        List<BranchDetailsEntity> allBranches = branchDetailsRepository.findAllByRegionName("DHAKA REGION");
        assertThat(allBranches.size()).isEqualTo(2);
    }


    BranchDetailsEntity buildBranchEntity(String oid) {
        return BranchDetailsEntity.builder()
                .branchOid(oid)
                .branchCode("wueiyew")
                .branchName("Dhaka")
                .branchOid("ooeiuiue")
                .address("aklsdk")
                .branchName("Dhaka branch")
                .regionName("DHAKA REGION")
                .build();
    }

    List<BranchDetailsEntity> buildBranchEntityList() {
        return Arrays.asList(buildBranchEntity("fkajdhfkadhfkahd"));
    }

}