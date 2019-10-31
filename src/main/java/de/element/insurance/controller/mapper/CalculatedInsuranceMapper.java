package de.element.insurance.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import de.element.insurance.dataacessobject.CalculatedInsuranceDTO;
import de.element.insurance.domainobject.CalculatedInsurance;

public class CalculatedInsuranceMapper {

	public static CalculatedInsurance makeCalculatedInsurance(CalculatedInsuranceDTO calculatedDTO) {
		return new CalculatedInsurance(0l, InsuranceMapper.makeInsuranceModule(calculatedDTO.getInsuranceModule()), 
				calculatedDTO.getCoverage(), calculatedDTO.getPrice());
	}

	public static CalculatedInsuranceDTO makeCalculatedInsuranceDTO(CalculatedInsurance calculatedInsurance) {
		CalculatedInsuranceDTO.CalculatedInsuranceDTOBuilder calculatedInsuranceDTOBuilder = CalculatedInsuranceDTO.newBuilder()
				.setId(calculatedInsurance.getId())
				.setCoverage(calculatedInsurance.getCoverage())
				.setPrice(calculatedInsurance.getPrice())
				.setInsuranceModule(InsuranceMapper.makeInsuranceModuleDTO(calculatedInsurance.getInsuranceModule()));
		return calculatedInsuranceDTOBuilder.createCalculatedInsuranceDTO();
	}

	public static List<CalculatedInsuranceDTO> makeCalculatedInsuranceDTO(List<CalculatedInsurance> calculatedInsurances) {
		return calculatedInsurances.stream()
				.map(CalculatedInsuranceMapper::makeCalculatedInsuranceDTO)
				.collect(Collectors.toList());
	}
}
