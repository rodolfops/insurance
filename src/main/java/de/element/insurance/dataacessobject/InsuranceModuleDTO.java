package de.element.insurance.dataacessobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceModuleDTO {

	@JsonIgnore
    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;
    
    @PositiveOrZero
    private double minimumCoverage;
    @Positive
    private double maximumCoverage;
    @PositiveOrZero
    private double risk;
    
    public InsuranceModuleDTO() { }
    
    public InsuranceModuleDTO(Long id, String name, double minCoverage,
			double maxCoverage, double risk) {
    	this.id = id;
    	this.name = name;
    	this.minimumCoverage = minCoverage;
    	this.maximumCoverage = maxCoverage;
    	this.risk = risk;
    }

	@JsonProperty
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public double getMinimumCoverage() {
		return minimumCoverage;
	}

	public double getMaximumCoverage() {
		return maximumCoverage;
	}

	public double getRisk() {
		return risk;
	}
	
	public static InsuranceModuleDTOBuilder newBuilder() {
		return new InsuranceModuleDTOBuilder();
	}
	
    public static class InsuranceModuleDTOBuilder {

    	private Long id;
        private String name;
        private double minimumCoverage;
        private double maximumCoverage;
        private double risk;
		
        public InsuranceModuleDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
        
		public InsuranceModuleDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public InsuranceModuleDTOBuilder setMinimumCoverage(double minimumCoverage) {
			this.minimumCoverage = minimumCoverage;
			return this;
		}
		
		public InsuranceModuleDTOBuilder setMaximumCoverage(double maximumCoverage) {
			this.maximumCoverage = maximumCoverage;
			return this;
		}
		
		public InsuranceModuleDTOBuilder setRisk(double risk) {
			this.risk = risk;
			return this;
		}
        
		public InsuranceModuleDTO createInsuranceModuleDTO() {
			return new InsuranceModuleDTO(id, name, minimumCoverage, maximumCoverage, risk);
		}
		
    }
}
