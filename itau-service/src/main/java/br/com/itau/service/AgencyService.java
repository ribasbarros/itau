package br.com.itau.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.service.exceptions.AgencyNotFoundException;
import br.com.itau.service.exceptions.InvalidParameterException;

public interface AgencyService {

	Integer findByPlaceId(String placeId) throws AgencyNotFoundException, InvalidParameterException;

	AgencyEntity create(AgencyEntity entity) throws InvalidParameterException;

	Collection<AgencyEntity> findAll();

	AgencyEntity find(Long id) throws AgencyNotFoundException;

	void update(Long id, AgencyEntity entity) throws AgencyNotFoundException;

	void delete(Long id) throws AgencyNotFoundException;

	Page<AgencyEntity> search(int page, int size);
	
}
