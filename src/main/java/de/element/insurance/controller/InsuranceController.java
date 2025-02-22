package de.element.insurance.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.element.insurance.controller.mapper.InsuranceMapper;
import de.element.insurance.dataacessobject.InsuranceModuleDTO;
import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.ConstraintsViolationException;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.service.insurance.InsuranceService;

@RestController
@RequestMapping(value="/api/insurances")
public class InsuranceController {

	private final InsuranceService insuranceService;
    private final ModelMapper mapper;
	
	@Autowired
	public InsuranceController(final InsuranceService insuranceService, ModelMapper mapper) {
		this.insuranceService = insuranceService;
		this.mapper = mapper;
	}

	@GetMapping
	public List<InsuranceModuleDTO> findAll(Pageable pageable){
		return InsuranceMapper.makeInsuranceDTOList(insuranceService.findAll(pageable));
	}
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public InsuranceModuleDTO create(@RequestBody InsuranceModuleDTO insuranceToCreateDTO) throws ConstraintsViolationException {
		InsuranceModule insuranceModule = InsuranceMapper.makeInsuranceModule(insuranceToCreateDTO);
		InsuranceModule insuranceSaved = insuranceService.save(insuranceModule);
		return mapper.map(insuranceSaved, InsuranceModuleDTO.class);
//		return InsuranceMapper.makeInsuranceModuleDTO(insuranceSaved);
	}
	
	@PutMapping("/{id}")
	public InsuranceModuleDTO update(@PathVariable Long id, @RequestBody InsuranceModuleDTO updateInsuranceDTO)
			 throws EntityNotFoundException {
		InsuranceModule insuranceModule = InsuranceMapper.makeInsuranceModule(updateInsuranceDTO);
		return InsuranceMapper.makeInsuranceModuleDTO(insuranceService.update(id, insuranceModule));
	}
	
	@PatchMapping("/{id}")
	public InsuranceModuleDTO partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields)
			 throws EntityNotFoundException {
		return InsuranceMapper.makeInsuranceModuleDTO(insuranceService.partialUpdate(id, fields));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws EntityNotFoundException {
		insuranceService.delete(id);
	}
	
}
