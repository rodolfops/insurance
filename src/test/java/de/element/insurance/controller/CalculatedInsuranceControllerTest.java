package de.element.insurance.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.element.insurance.dataacessobject.CalculatedInsuranceDTO;
import de.element.insurance.dataacessobject.InsuranceModuleDTO;
import de.element.insurance.domainobject.CalculatedInsurance;
import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.service.calculatedinsurance.CalculatedInsuranceService;

@RunWith(SpringRunner.class)
@WebMvcTest(CalculatedInsuranceController.class)
public class CalculatedInsuranceControllerTest {

	@MockBean
	private CalculatedInsuranceService calculatedInsuranceService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<List<CalculatedInsuranceDTO>> jsonList;
	private JacksonTester<CalculatedInsuranceDTO> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void findAllCalculatedInsuranceTest() throws Exception {
		// given
		Pageable pageable = PageRequest.of(0, 20);
		// entity list
		InsuranceModule insurance = new InsuranceModule(1l, "Bike", 0, 3000,
				0.3);
		CalculatedInsurance calculatedInsurance = new CalculatedInsurance(1l,
				insurance, 1000, 300);
		List<CalculatedInsurance> calculatedList = new ArrayList<>();
		Collections.addAll(calculatedList, calculatedInsurance);
		// dto list
		InsuranceModuleDTO insuranceDTO = new InsuranceModuleDTO(1l, "Bike", 0,
				3000, 0.3);
		CalculatedInsuranceDTO calculatedDTO = new CalculatedInsuranceDTO(1l,
				1000.0, 300.0, insuranceDTO);
		List<CalculatedInsuranceDTO> calculatedDTOList = new ArrayList<>();
		Collections.addAll(calculatedDTOList, calculatedDTO);

		given(calculatedInsuranceService.findCalculatedInsurances(pageable))
				.willReturn(calculatedList);
		// when
		MockHttpServletResponse response = mvc
				.perform(
						get("/api/calculated-insurances").accept(
								MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonList.write(calculatedDTOList).getJson());
	}

	@Test
	public void calculateInsurancePriceTest() throws Exception {
		// entity
		InsuranceModule insurance = new InsuranceModule(1l, "Test", 0, 3000, 0.3);
		CalculatedInsurance calculatedInsurance = new CalculatedInsurance(1l, insurance, 1000, 300);
		// dto 
		InsuranceModuleDTO insuranceDTO = new InsuranceModuleDTO(1l, "Test", 0, 3000, 0.3);
		CalculatedInsuranceDTO calculatedDTO = new CalculatedInsuranceDTO(1l, 1000.0, 300.0, insuranceDTO);
		// given
		given(calculatedInsuranceService.calculateInsurancePrice("Test",1000.0))
				.willReturn(calculatedInsurance);
		// when
		MockHttpServletResponse response = mvc
				.perform(
						get("/api/calculated-insurances/price?name=Test&coverage=1000.0").accept(
								MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				json.write(calculatedDTO).getJson());
	}

	@Test
	public void calculateInsurancePriceTestWithNegativeValue() throws Exception {
		// when
		MockHttpServletResponse response = mvc
				.perform(
						get("/api/calculated-insurances/price?name=Test&coverage=-10.0").accept(
								MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void calculateInsurancePriceTestWithEmptyName() throws Exception {
		// when
		MockHttpServletResponse response = mvc
				.perform(
						get("/api/calculated-insurances/price?coverage=1000.0").accept(
								MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
}
