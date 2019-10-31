package de.element.insurance.service.calculatedinsurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.element.insurance.domainobject.CalculatedInsurance;
import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.repository.CalculatedInsuranceRepository;
import de.element.insurance.repository.InsuranceModuleRepository;

@Service
public class CalculatedInsuranceServiceImpl implements CalculatedInsuranceService {
	
	private InsuranceModuleRepository insuranceModuleRepository;
	private CalculatedInsuranceRepository calculatedInsuranceRepository;

	@Autowired
	public CalculatedInsuranceServiceImpl(
			InsuranceModuleRepository insuranceModuleRepository,
			CalculatedInsuranceRepository calculatedInsuranceRepository) {
		this.insuranceModuleRepository = insuranceModuleRepository;
		this.calculatedInsuranceRepository = calculatedInsuranceRepository;
	}

	@Override
	@Transactional
	public CalculatedInsurance calculateInsurancePrice(String name, double coverage) throws EntityNotFoundException {
		InsuranceModule insurance = insuranceModuleRepository.findByName(name)
	            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with name: " + name));
		double price = coverage * insurance.getRisk();
		CalculatedInsurance calculatedInsurance = new CalculatedInsurance(0l, insurance, coverage, price);
		return calculatedInsuranceRepository.save(calculatedInsurance);
	}

	@Override
	public List<CalculatedInsurance> findCalculatedInsurances(Pageable pageable) {
		return calculatedInsuranceRepository.findAll(pageable).getContent();
	}

}
