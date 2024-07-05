package net.celloscope.api.restriction.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarySearchRestrictionRepository extends CrudRepository<BeneficiarySearchRestriction, String> {}