package de.element.insurance.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="calculated_insurances")
public class CalculatedInsurance {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="insurance_module")
	private InsuranceModule insuranceModule;
	
	private double coverage;
	
	private double price;

	public CalculatedInsurance() { }

	public CalculatedInsurance(Long id, InsuranceModule insurance, double coverage,
			double price) {
		this.id = id;
		this.coverage = coverage;
		this.price = price;
		this.insuranceModule = insurance;
	}

	public Long getId() {
		return id;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public InsuranceModule getInsuranceModule() {
		return insuranceModule;
	}

	public double getCoverage() {
		return coverage;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(coverage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((insuranceModule == null) ? 0 : insuranceModule.hashCode());
		temp = Double.doubleToLongBits(price);
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
		CalculatedInsurance other = (CalculatedInsurance) obj;
		if (Double.doubleToLongBits(coverage) != Double
				.doubleToLongBits(other.coverage))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuranceModule == null) {
			if (other.insuranceModule != null)
				return false;
		} else if (!insuranceModule.equals(other.insuranceModule))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}
}
