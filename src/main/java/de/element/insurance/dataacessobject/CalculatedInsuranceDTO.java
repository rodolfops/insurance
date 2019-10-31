package de.element.insurance.dataacessobject;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculatedInsuranceDTO {

	@JsonIgnore
    private Long id;
	
	@Positive
	private double coverage;
	
	@Positive
	private double price;
	
	private InsuranceModuleDTO insuranceModule;

	public CalculatedInsuranceDTO() { }
	
	public CalculatedInsuranceDTO(Long id, double coverage, double price,
			InsuranceModuleDTO insurance) {
		this.id = id;
		this.coverage = coverage;
		this.price = price;
		this.insuranceModule = insurance;
	}

	@JsonProperty
    public Long getId() {
        return id;
    }

	public double getCoverage() {
		return coverage;
	}

	public double getPrice() {
		return price;
	}

	public InsuranceModuleDTO getInsuranceModule() {
		return insuranceModule;
	}
	
	public static CalculatedInsuranceDTOBuilder newBuilder() {
		return new CalculatedInsuranceDTOBuilder();
	}
	
	public static class CalculatedInsuranceDTOBuilder {

		private Long id;
		private double coverage;
		private double price;
		private InsuranceModuleDTO insuranceModule;
		
		public CalculatedInsuranceDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public CalculatedInsuranceDTOBuilder setCoverage(double coverage) {
			this.coverage = coverage;
			return this;
		}

		public CalculatedInsuranceDTOBuilder setPrice(double price) {
			this.price = price;
			return this;
		}

		public CalculatedInsuranceDTOBuilder setInsuranceModule(InsuranceModuleDTO insuranceModule) {
			this.insuranceModule = insuranceModule;
			return this;
		}

		public CalculatedInsuranceDTO createCalculatedInsuranceDTO() {
			return new CalculatedInsuranceDTO(id, coverage, price, insuranceModule);
		}
	}
}
