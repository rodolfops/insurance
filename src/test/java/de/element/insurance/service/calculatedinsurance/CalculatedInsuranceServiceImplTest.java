package de.element.insurance.service.calculatedinsurance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import de.element.insurance.domainobject.CalculatedInsurance;
import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.repository.CalculatedInsuranceRepository;
import de.element.insurance.repository.InsuranceModuleRepository;

public class CalculatedInsuranceServiceImplTest {

	private CalculatedInsuranceServiceImpl calculatedInsuranceServiceImpl;

	@Mock
	private CalculatedInsuranceRepository calculatedInsuranceRepository;
	@Mock
	private InsuranceModuleRepository insuranceRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		calculatedInsuranceServiceImpl = new CalculatedInsuranceServiceImpl(
				insuranceRepository, calculatedInsuranceRepository);
	}
	
	@Test
	public void findCalculatedInsurancesTest() {
		//given
		InsuranceModule insurance = new InsuranceModule(1l, "Bike", 0, 3000, 0.3);
		CalculatedInsurance calculatedInsurance = new CalculatedInsurance(1l, insurance, 1000, 300);
		List<CalculatedInsurance> calculatedInsuranceList = new ArrayList<>();
		Collections.addAll(calculatedInsuranceList, calculatedInsurance);
		Page<CalculatedInsurance> page = new PageImpl<CalculatedInsurance>(calculatedInsuranceList);
		Pageable pageable = PageRequest.of(0, 20);
		given(calculatedInsuranceRepository.findAll(pageable)).willReturn(page);
		//when
		List<CalculatedInsurance> returnedList = calculatedInsuranceServiceImpl.findCalculatedInsurances(pageable);
		//then
		assertThat(returnedList).isEqualTo(calculatedInsuranceList);
	}
	
	@Test
	public void findEmptyCalculatedInsurancesTest() {
		//given
		List<CalculatedInsurance> calculatedInsuranceList = new ArrayList<>();
		Page<CalculatedInsurance> page = new PageImpl<CalculatedInsurance>(calculatedInsuranceList);
		Pageable pageable = PageRequest.of(0, 20);
		given(calculatedInsuranceRepository.findAll(pageable)).willReturn(page);
		//when
		List<CalculatedInsurance> returnedList = calculatedInsuranceServiceImpl.findCalculatedInsurances(pageable);
		//then
		assertThat(returnedList).isEqualTo(calculatedInsuranceList);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void calculatePriceTestWithError() throws EntityNotFoundException {
		//given
		Optional<InsuranceModule> optional = Optional.empty();
		given(insuranceRepository.findByName("Test")).willReturn(optional);
		//when
		calculatedInsuranceServiceImpl.calculateInsurancePrice("Test", 1000.0);
	}
	
	@Test
	public void calculatePriceTest() throws EntityNotFoundException {
		//given
		InsuranceModule insurance = new InsuranceModule(1l, "Sports", 0, 3000.0, 0.5);
		Optional<InsuranceModule> optional = Optional.ofNullable(insurance);
		CalculatedInsurance calculatedInsurance = new CalculatedInsurance(0l, insurance, 1000.0, 500.0);
		CalculatedInsurance savedCalculated = new CalculatedInsurance(1l, insurance, 1000.0, 500.0);
		given(insuranceRepository.findByName("Sports")).willReturn(optional);
		given(calculatedInsuranceRepository.save(calculatedInsurance)).willReturn(savedCalculated);
		//when
		CalculatedInsurance price = calculatedInsuranceServiceImpl.calculateInsurancePrice("Sports", 1000.0);
		//then
		assertThat(price).isEqualTo(savedCalculated);
		assertEquals(price.getPrice(), 500.0, 0.1);
	}
}
