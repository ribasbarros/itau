package br.com.itau.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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

import br.com.itau.api.request.CollectRequest;
import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.helper.ReturnMessage;
import br.com.itau.test.helper.ObjectsHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
public class CollectControllerTest extends ObjectsHelper {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setup() {
		
	}

	@Test
	public void testCollectControllerCreateWithNotFound() {

		CollectRequest request = objectCollectRequest();

		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.postForEntity("/api/collect/v1", request, ReturnMessage.class);

		@SuppressWarnings("unchecked")
		Map<Object, Object> transform = (HashMap<Object, Object>) response.getBody().getResult();
		
		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
		assertFalse(transform != null);
		assertTrue(response.getBody().getDetalhe() != null);
		

		_collectRepository.deleteAll();

	}
	
	@Test
	public void testCollectControllerCreateWithBadRequest() {

		CollectRequest request = objectCollectRequest();
		request.setPlaceId(null);

		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.postForEntity("/api/collect/v1", request, ReturnMessage.class);

		@SuppressWarnings("unchecked")
		Map<Object, Object> transform = (HashMap<Object, Object>) response.getBody().getResult();
		
		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
		assertFalse(transform != null);
		assertTrue(response.getBody().getDetalhe() != null);
		
		_collectRepository.deleteAll();

	}
	
	@Test
	public void testCollectControllerCreateWithSuccess() {

		CollectRequest request = objectCollectRequest();
		request.setPlaceId(PLACE_ID_DEFAULT);
		
		Integer agencyNumber = RandomUtils.nextInt();
		
		_agencyRepository.save(new AgencyEntity(
				EMPTY_LONG, 
				PLACE_ID_DEFAULT, 
				agencyNumber, 
				EMPTY_DATE));

		@SuppressWarnings("rawtypes")
		ResponseEntity<ReturnMessage> response = restTemplate.postForEntity("/api/collect/v1", request, ReturnMessage.class);
		
		@SuppressWarnings("unchecked")
		Map<Object, Object> transform = (HashMap<Object, Object>) response.getBody().getResult();

		assertNotNull(response);
		assertTrue(response.getStatusCodeValue() == HttpStatus.CREATED.value());
		assertTrue(transform != null);
		assertEquals(Integer.valueOf(transform.get("agency").toString()), agencyNumber);
		assertTrue(response.getBody().getDetalhe() == null);
		
		_agencyRepository.deleteAll();
		_collectRepository.deleteAll();

	}
	
}
