package de.element.insurance.service.insurance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.ConstraintsViolationException;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.repository.InsuranceModuleRepository;

public class InsuranceServiceImplTest {

	private InsuranceServiceImpl insuranceServiceImpl;
	
	@Mock
	private InsuranceModuleRepository insuranceRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		insuranceServiceImpl = new InsuranceServiceImpl(insuranceRepository);
	}
	
	@Test
	public void findAllTest() {
		//given
		InsuranceModule insurance = new InsuranceModule(1l, "Bike", 0, 3000, 0.3);
		List<InsuranceModule> insuranceList = new ArrayList<>();
		Collections.addAll(insuranceList, insurance);
		Pageable pageable = PageRequest.of(0, 20);
		given(insuranceRepository.findAllByDeletedFalse(pageable)).willReturn(insuranceList);
		//when
		List<InsuranceModule> returnedList = insuranceServiceImpl.findAll(pageable);
		//then
		assertThat(returnedList).isEqualTo(insuranceList);
	}
	
	@Test
	public void saveTest() throws ConstraintsViolationException {
		//given
		InsuranceModule insuranceToCreate = new InsuranceModule(0l, "Bike", 0, 3000, 0.3);
		InsuranceModule insuranceCreated = new InsuranceModule(1l, "Bike", 0, 3000, 0.3);
		given(insuranceRepository.save(insuranceToCreate)).willReturn(insuranceCreated);
		//when
		InsuranceModule insuranceSaved = insuranceServiceImpl.save(insuranceToCreate);
		//then
		assertThat(insuranceSaved).isEqualTo(insuranceCreated);
	}
	
	@Test
	public void updateTest() throws EntityNotFoundException {
		//given
		InsuranceModule insurance = new InsuranceModule(1l, "Bike", 0, 3000, 0.3);
		Optional<InsuranceModule> optional = Optional.ofNullable(insurance);
		InsuranceModule insuranceToUpdate = new InsuranceModule(1l, "Jewelry", 0, 1000, 0.3);
		given(insuranceRepository.findById(1l)).willReturn(optional);
		given(insuranceRepository.save(insuranceToUpdate)).willReturn(insuranceToUpdate);
		//when
		InsuranceModule newInsurance = insuranceServiceImpl.update(1l, insuranceToUpdate);
		
		//then
		assertThat(newInsurance).isEqualTo(insuranceToUpdate);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void updateTestWithException() throws EntityNotFoundException {
		//given
		Optional<InsuranceModule> optional = Optional.empty();
		InsuranceModule insuranceToUpdate = new InsuranceModule(1l, "Jewelry", 0, 1000, 0.3);
		given(insuranceRepository.findById(1l)).willReturn(optional);
		//when
		insuranceServiceImpl.update(1l, insuranceToUpdate);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void deleteTestWithException() throws EntityNotFoundException {
		//given
		Optional<InsuranceModule> optional = Optional.empty();
		given(insuranceRepository.findById(1l)).willReturn(optional);
		//when
		insuranceServiceImpl.delete(1l);
	}
}
