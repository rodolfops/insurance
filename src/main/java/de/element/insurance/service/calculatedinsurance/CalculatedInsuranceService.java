package de.element.insurance.service.calculatedinsurance;

import java.util.List;

import org.springframework.data.domain.Pageable;

import de.element.insurance.domainobject.CalculatedInsurance;
import de.element.insurance.exception.EntityNotFoundException;

public interface CalculatedInsuranceService {

	CalculatedInsurance calculateInsurancePrice(String name, double coverage) throws EntityNotFoundException;
	
	List<CalculatedInsurance> findCalculatedInsurances(Pageable pageable);
}
