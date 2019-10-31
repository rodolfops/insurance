package de.element.insurance.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.element.insurance.controller.mapper.CalculatedInsuranceMapper;
import de.element.insurance.dataacessobject.CalculatedInsuranceDTO;
import de.element.insurance.domainobject.CalculatedInsurance;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.exception.InvalidParamException;
import de.element.insurance.service.calculatedinsurance.CalculatedInsuranceService;

@RestController
@RequestMapping(value="/api/calculated-insurances")
public class CalculatedInsuranceController {

	private final CalculatedInsuranceService calculatedInsuranceService;
	
	@Autowired
	public CalculatedInsuranceController(
			final CalculatedInsuranceService calculatedInsuranceService) {
		this.calculatedInsuranceService = calculatedInsuranceService;
	}

	@GetMapping(value="/price")
	public CalculatedInsuranceDTO calculateInsurancePrice(@RequestParam @NotNull String name, 
			@RequestParam double coverage) throws EntityNotFoundException, InvalidParamException {
		if(coverage < 0) {
			throw new InvalidParamException("Coverage could not be negative");
		}
		CalculatedInsurance calculateInsurancePrice = calculatedInsuranceService.calculateInsurancePrice(name, coverage);
		return CalculatedInsuranceMapper.makeCalculatedInsuranceDTO(calculateInsurancePrice);
		
	}
	
	@GetMapping
	public List<CalculatedInsuranceDTO> findAllCalculatedInsurance(Pageable pageable){
		return CalculatedInsuranceMapper.makeCalculatedInsuranceDTO(calculatedInsuranceService.findCalculatedInsurances(pageable));
	}
}
