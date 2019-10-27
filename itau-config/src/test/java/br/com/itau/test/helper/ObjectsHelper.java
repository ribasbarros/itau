package br.com.itau.test.helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.api.request.AgencyRequest;
import br.com.itau.api.request.CollectRequest;
import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.domain.repository.AgencyRepository;
import br.com.itau.domain.repository.CollectRepository;
import br.com.itau.helper.ConstantsHelper;

public class ObjectsHelper extends ConstantsHelper {

	@Autowired
	public AgencyRepository _agencyRepository;
	
	@Autowired
	public CollectRepository _collectRepository;
	
	public CollectRequest objectCollectRequest() {
		return new CollectRequest(
				RandomUtils.nextDouble(), 
				RandomUtils.nextDouble(), 
				RandomStringUtils.randomAlphabetic(80), 
				RandomStringUtils.randomAlphabetic(10));
	}
	
	public AgencyEntity persistAgencyEntity() {
		return _agencyRepository.save(
				new AgencyEntity(
						EMPTY_LONG, 
						RandomStringUtils.randomAlphabetic(50), 
						RandomUtils.nextInt(), 
						EMPTY_DATE));
	}
	
	public AgencyRequest objectAgencyRequest() { 
		return new AgencyRequest(
				EMPTY_LONG, 
				RandomStringUtils.randomAlphabetic(10), 
				RandomUtils.nextInt());
	}
	
}
