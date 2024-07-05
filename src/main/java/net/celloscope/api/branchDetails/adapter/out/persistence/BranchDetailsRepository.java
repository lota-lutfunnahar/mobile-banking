package net.celloscope.api.branchDetails.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchDetailsRepository extends JpaRepository<BranchDetailsEntity,String> {
   List<BranchDetailsEntity> findAllByRegionName(String regionName);

   @Query(value = "select distinct regionname from branch b order by regionname asc", nativeQuery = true)
   List<String> findAllRegionName();
}
