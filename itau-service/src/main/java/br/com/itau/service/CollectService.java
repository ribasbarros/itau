package br.com.itau.service;

import br.com.itau.domain.entity.CollectEntity;
import br.com.itau.service.exceptions.InvalidParameterException;

public interface CollectService {

	CollectEntity create(CollectEntity entity) throws InvalidParameterException;

}
