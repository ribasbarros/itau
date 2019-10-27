package br.com.itau.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.itau.api.controller.convert.AgencyHelper;
import br.com.itau.api.request.AgencyRequest;
import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.helper.ReturnMessage;
import br.com.itau.test.helper.ObjectsHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
public class AgencyControllerTest extends ObjectsHelper {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setup() {
		
	}

	@Test
	public void testAgencyControllerCreateWithSuccess() {
		AgencyRequest request = objectAgencyRequest();

		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.postForEntity("/api/agency/v1", request, ReturnMessage.class);

		@SuppressWarnings("unchecked")
		Map<Object, Object> transform = (HashMap<Object, Object>) response.getBody().getResult();

		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.CREATED.value());
		assertTrue(transform != null);
		assertEquals(Integer.valueOf(transform.get("agencyNumber").toString()), request.getAgencyNumber());
		assertTrue(response.getBody().getDetalhe() == null);
		
		_agencyRepository.deleteAll();

	}
	
	@Test
	public void testAgencyControllerCreateBadRequest() {
		AgencyRequest request = objectAgencyRequest();
		request.setPlaceId(null);
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.postForEntity("/api/agency/v1", request, ReturnMessage.class);

		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
		
		_agencyRepository.deleteAll();

	}
	
	@Test
	public void testAgencyControllerFindWithSuccess() {
		AgencyEntity created = persistAgencyEntity();

		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.getForEntity("/api/agency/v1/{id}", ReturnMessage.class, created.getId());

		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());

		@SuppressWarnings("unchecked")
		Map<Object, Object> transform = (HashMap<Object, Object>) response.getBody().getResult();
		
		assertNotNull(transform.get("placeId"));
		assertNotNull(transform.get("agencyNumber"));
		
		_agencyRepository.delete(created);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAgencyControllerSearchWithSuccess() {
		
		persistAgencyEntity();

		ResponseEntity<Object> responseObject = restTemplate.getForEntity(
				"/api/agency/v1/search/?page=0&size=20", 
				Object.class);
		
		Map<?,?> response = (Map<?, ?>) responseObject.getBody();
		

		assertNotNull(response);
		assertTrue( ((ArrayList<AgencyEntity>) response.get("content")).size() == 1);
		
		for(int i=0 ; i < 19 ; i++) persistAgencyEntity();
		
		responseObject = restTemplate.getForEntity(
				"/api/agency/v1/search/?page=0&size=10", 
				Object.class);
		
		response = (Map<?, ?>) responseObject.getBody();
		
		assertNotNull(response);
		assertTrue( ((ArrayList<AgencyEntity>) response.get("content")).size() == 10);
		
		_agencyRepository.deleteAll();
	}
	
	@Test
	public void testAgencyControllerUpdateWithSuccess() {
		AgencyEntity entity = persistAgencyEntity();
		AgencyRequest request = AgencyHelper.convertToRequest(entity);

		String updated = String.format("UPDATED_%s", RandomStringUtils.randomAlphabetic(10));

		String before = entity.getPlaceId();
		request.setPlaceId(updated);

		restTemplate.put("/api/agency/v1/{id}", request, request.getId());

		Optional<AgencyEntity> result = _agencyRepository.findById(entity.getId());

		assertNotEquals(updated, before);
		assertEquals(result.get().getPlaceId(), updated);
		assertNotEquals(result.get().getPlaceId(), before);
		
		_agencyRepository.deleteAll();
	}
	
	@Test
	public void testAgencyControllerUpdateNotFound() {
		AgencyEntity entity = persistAgencyEntity();
		AgencyRequest request = AgencyHelper.convertToRequest(entity);

		String updated = String.format("UPDATED_%s", RandomStringUtils.randomAlphabetic(10));

		String before = entity.getPlaceId();
		request.setPlaceId(updated);

		restTemplate.put("/api/agency/v1/{id}", request, RandomUtils.nextInt());

		Optional<AgencyEntity> result = _agencyRepository.findById(entity.getId());

		assertNotEquals(updated, before);
		assertNotEquals(result.get().getPlaceId(), updated);
		assertEquals(result.get().getPlaceId(), before);
		
		_agencyRepository.deleteAll();
	}
	
	@Test
	public void testAgencyControllerDeleteWithSuccess() {
		AgencyEntity entity = persistAgencyEntity();
		
		restTemplate.delete("/api/agency/v1/{id}", entity.getId());

		Optional<AgencyEntity> result = _agencyRepository.findById(entity.getId());

		assertFalse(result.isPresent());
	}
	
}
