package br.com.itau.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.domain.repository.AgencyRepository;
import br.com.itau.helper.ConstantsHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
public class AgencyRepositoryTest extends ConstantsHelper {

	@Autowired
	private AgencyRepository _repository;

	@Before
	public void setup() {

	}

	@After
	public void rollback() {
		_repository.deleteAll();
	}

	@Test
	public void testeAgencyRepositoryConstructorAllArgmentsWithSuccess() {

		_repository.save(
				new AgencyEntity(
						EMPTY_LONG, 
						RandomStringUtils.randomAlphabetic(50), 
						RandomUtils.nextInt(), 
						EMPTY_DATE));

		List<AgencyEntity> result = _repository.findAll();

		assertNotNull(result);
		assertTrue(result.size() == 1);

	}

	@Test(expected = Exception.class)
	public void testeAgencyRepositoryConstructorAllArgmentsNotSuccess() {

		_repository.save(
				new AgencyEntity(
						EMPTY_LONG, 
						EMPTY_STRING, 
						RandomUtils.nextInt(), 
						EMPTY_DATE));

		List<AgencyEntity> result = _repository.findAll();

		assertNull(result);

	}
	
	@Test(expected = Exception.class)
	public void testeAgencyRepositoryConstructorNoArgmentsNotSuccess() {

		_repository.save(
				new AgencyEntity());

		List<AgencyEntity> result = _repository.findAll();

		assertNull(result);

	}

}
