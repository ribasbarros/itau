package br.com.itau.api.controller.convert;

import br.com.itau.api.request.CollectRequest;
import br.com.itau.api.response.CollectResponse;
import br.com.itau.domain.entity.CollectEntity;
import br.com.itau.helper.ConstantsHelper;

public class CollectHelper extends ConstantsHelper {

	public static CollectEntity convert(CollectRequest request) {
		return new CollectEntity(
				EMPTY_LONG, 
				request.getLat(), 
				request.getLng(), 
				request.getBrowserDevice(), 
				EMPTY_INT, 
				EMPTY_DATE);
	}

	public static CollectResponse convert(CollectEntity entity) {
		return new CollectResponse(
				entity.getId(), 
				entity.getAgency());
	}

}
