package br.com.itau.service.imp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.domain.entity.CollectEntity;
import br.com.itau.domain.repository.CollectRepository;
import br.com.itau.service.CollectService;
import br.com.itau.service.exceptions.InvalidParameterException;

@Service
public class CollectServiceImpl implements CollectService {

	@Autowired
	private CollectRepository _repository;
	
	@Override
	public CollectEntity create(@Valid CollectEntity entity) throws InvalidParameterException {
		if(null == entity) throw new InvalidParameterException();
		
		return _repository.save(entity);
	}

}
