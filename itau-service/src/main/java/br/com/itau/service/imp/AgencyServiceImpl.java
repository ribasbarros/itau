package br.com.itau.service.imp;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.domain.repository.AgencyRepository;
import br.com.itau.service.AgencyService;
import br.com.itau.service.exceptions.AgencyNotFoundException;
import br.com.itau.service.exceptions.InvalidParameterException;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgencyRepository _repository;

	@Override
	public Integer findByPlaceId(String placeId) throws AgencyNotFoundException, InvalidParameterException {

		if (null == placeId)
			throw new InvalidParameterException();

		Optional<AgencyEntity> agency = _repository.findByPlaceId(placeId);

		if (agency.isPresent())
			return agency.get().getAgencyNumber();

		throw new AgencyNotFoundException();
	}

	@Override
	public AgencyEntity create(AgencyEntity entity) throws InvalidParameterException {
		if (null == entity)
			throw new InvalidParameterException();
		return _repository.save(entity);
	}

	@Override
	public Collection<AgencyEntity> findAll() {
		return Collections.unmodifiableCollection(_repository.findAll());
	}

	@Override
	public AgencyEntity find(Long id) throws AgencyNotFoundException {

		Optional<AgencyEntity> entity = _repository.findById(id);
		if (entity.isPresent())
			return entity.get();

		throw new AgencyNotFoundException();

	}

	@Override
	public void update(Long id, AgencyEntity entity) throws AgencyNotFoundException {
		
		Optional<AgencyEntity> oldAgency = _repository.findById(id);
		
		if(oldAgency.isPresent()) {
			entity.setDtCreation(oldAgency.get().getDtCreation());
			_repository.save(entity);
		}else {
			throw new AgencyNotFoundException();
		}
		
	}

	@Override
	public void delete(Long id) throws AgencyNotFoundException {
		AgencyEntity result = find(id);
		_repository.delete(result);
	}

	@Override
	public Page<AgencyEntity> search(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "agencyNumber");
		return _repository.findAll(pageRequest);
	}

}
