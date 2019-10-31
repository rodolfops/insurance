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

import de.element.insurance.dataacessobject.InsuranceModuleDTO;
import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.service.insurance.InsuranceService;

@RunWith(SpringRunner.class)
@WebMvcTest(InsuranceController.class)
public class InsuranceControllerTest {

	@MockBean
	private InsuranceService insuranceService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<List<InsuranceModuleDTO>> jsonList;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void findAllTest() throws Exception {
		// given
		Pageable pageable = PageRequest.of(0, 20);
		// entity list
		InsuranceModule insurance = new InsuranceModule(1l, "Bike", 0, 3000,
				0.3);
		List<InsuranceModule> insuranceList = new ArrayList<>();
		Collections.addAll(insuranceList, insurance);
		// dto list
		InsuranceModuleDTO insuranceDTO = new InsuranceModuleDTO(1l, "Bike", 0,
				3000, 0.3);
		List<InsuranceModuleDTO> insuranceDTOList = new ArrayList<>();
		Collections.addAll(insuranceDTOList, insuranceDTO);

		given(insuranceService.findAll(pageable))
				.willReturn(insuranceList);
		// when
		MockHttpServletResponse response = mvc
				.perform(
						get("/api/insurances").accept(
								MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonList.write(insuranceDTOList).getJson());
	}
	
}
