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

import br.com.itau.domain.entity.CollectEntity;
import br.com.itau.domain.repository.CollectRepository;
import br.com.itau.helper.ConstantsHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
public class CollectRepositoryTest extends ConstantsHelper {

	@Autowired
	private CollectRepository _repository;

	@Before
	public void setup() {

	}

	@After
	public void rollback() {
		_repository.deleteAll();
	}

	@Test
	public void testCollectRepositoryConstructorAllArgmentsWithSuccess() {

		_repository.save(
				new CollectEntity(
						EMPTY_LONG, 
						RandomUtils.nextDouble(), 
						RandomUtils.nextDouble(),
						RandomStringUtils.randomAlphabetic(80), 
						RandomUtils.nextInt(),
						EMPTY_DATE));

		List<CollectEntity> result = _repository.findAll();

		assertNotNull(result);
		assertTrue(result.size() == 1);

	}

	@Test(expected = Exception.class)
	public void testCollectRepositoryConstructorAllArgmentsNotSuccess() {

		_repository.save(
				new CollectEntity(
						EMPTY_LONG, 
						EMPTY_DOUBLE, 
						RandomUtils.nextDouble(),
						RandomStringUtils.randomAlphabetic(80), 
						RandomUtils.nextInt(),
						EMPTY_DATE));

		List<CollectEntity> result = _repository.findAll();

		assertNull(result);

	}
	
	@Test(expected = Exception.class)
	public void testCollectRepositoryConstructorNoArgmentsNotSuccess() {

		_repository.save(
				new CollectEntity());

		List<CollectEntity> result = _repository.findAll();

		assertNull(result);

	}

}
