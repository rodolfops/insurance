package de.element.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.element.insurance.domainobject.CalculatedInsurance;

public interface CalculatedInsuranceRepository extends JpaRepository<CalculatedInsurance, Long>{

}
