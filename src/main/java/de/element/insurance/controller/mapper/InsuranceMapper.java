package de.element.insurance.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.element.insurance.dataacessobject.InsuranceModuleDTO;
import de.element.insurance.domainobject.InsuranceModule;

public class InsuranceMapper {
	
	public static InsuranceModule makeInsuranceModule(InsuranceModuleDTO insuranceDTO) {
		return new InsuranceModule(insuranceDTO.getId(), insuranceDTO.getName(), insuranceDTO.getMinimumCoverage(), 
				insuranceDTO.getMaximumCoverage(), insuranceDTO.getRisk());
	}
	
	public static InsuranceModuleDTO makeInsuranceModuleDTO(InsuranceModule insurance) {
		InsuranceModuleDTO.InsuranceModuleDTOBuilder insuranceDTOBuilder = InsuranceModuleDTO.newBuilder()
				.setId(insurance.getId())
				.setName(insurance.getName())
				.setMinimumCoverage(insurance.getMinimumCoverage())
				.setMaximumCoverage(insurance.getMaximumCoverage())
				.setRisk(insurance.getRisk());
		
		return insuranceDTOBuilder.createInsuranceModuleDTO();
	}

	public static List<InsuranceModuleDTO> makeInsuranceDTOList(Collection<InsuranceModule> insurances) {
		return insurances.stream()
				.map(InsuranceMapper::makeInsuranceModuleDTO)
				.collect(Collectors.toList());
	}
}
