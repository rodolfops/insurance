package de.element.insurance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.element.insurance.domainobject.InsuranceModule;

@Repository
public interface InsuranceModuleRepository extends JpaRepository<InsuranceModule, Long>{

	Optional<InsuranceModule> findByName(String name);

	List<InsuranceModule> findAllByDeletedFalse(Pageable pageable);

}
