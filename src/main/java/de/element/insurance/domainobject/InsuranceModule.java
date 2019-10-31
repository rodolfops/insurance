package de.element.insurance.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="insurance_module")
public class InsuranceModule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    private Boolean deleted = false;
	
	@Column(name="minimum_coverage")
	private double minimumCoverage;
	
	@Column(name="maximum_coverage")
	private double maximumCoverage;
	
	private double risk;

	private String name;
	
	public InsuranceModule() { }
	
	public InsuranceModule(Long id, String name, double minCoverage, double maxCoverage, double risk) {
		this.id = id;
		this.name = name;
		this.minimumCoverage = minCoverage;
		this.maximumCoverage = maxCoverage;
		this.risk = risk;
	}
	
	public InsuranceModule(String name, double minCoverage, double maxCoverage, double risk) {
		this.name = name;
		this.minimumCoverage = minCoverage;
		this.maximumCoverage = maxCoverage;
		this.risk = risk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public double getMinimumCoverage() {
		return minimumCoverage;
	}

	public void setMinimumCoverage(double minimumCoverage) {
		this.minimumCoverage = minimumCoverage;
	}

	public double getMaximumCoverage() {
		return maximumCoverage;
	}

	public void setMaximumCoverage(double maximumCoverage) {
		this.maximumCoverage = maximumCoverage;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maximumCoverage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minimumCoverage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(risk);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InsuranceModule other = (InsuranceModule) obj;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(maximumCoverage) != Double
				.doubleToLongBits(other.maximumCoverage))
			return false;
		if (Double.doubleToLongBits(minimumCoverage) != Double
				.doubleToLongBits(other.minimumCoverage))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(risk) != Double
				.doubleToLongBits(other.risk))
			return false;
		return true;
	}
}
