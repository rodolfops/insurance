package de.element.insurance.service.insurance;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.ConstraintsViolationException;
import de.element.insurance.exception.EntityNotFoundException;

public interface InsuranceService {
	
	List<InsuranceModule> findAll(Pageable pageable);

	InsuranceModule save(InsuranceModule insuranceToCreate) throws ConstraintsViolationException;

	InsuranceModule update(Long id, InsuranceModule insuranceToUpdate) throws EntityNotFoundException;
	
	InsuranceModule partialUpdate(Long id, Map<String, Object> updates) throws EntityNotFoundException;

	void delete(Long id) throws EntityNotFoundException;
	
}
