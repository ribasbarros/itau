package br.com.itau.api.controller.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.itau.api.request.AgencyRequest;
import br.com.itau.api.response.AgencyResponse;
import br.com.itau.domain.entity.AgencyEntity;

public class AgencyHelper {

	public static AgencyEntity convert(AgencyRequest request) {
		return new AgencyEntity(
				request.getId(), 
				request.getPlaceId(), 
				request.getAgencyNumber(), 
				null);
		
	}

	public static AgencyResponse convert(AgencyEntity entity) {
		return new AgencyResponse(
				entity.getId(), 
				entity.getPlaceId(), 
				entity.getAgencyNumber(), 
				entity.getDtCreation());
	}

	public static AgencyResponse[] convert(Collection<AgencyEntity> entities) {
		List<AgencyResponse> result = new ArrayList<>();
		entities.forEach((agency) -> result.add(convert(agency)));
		return result.toArray(new AgencyResponse[result.size()]);
	}

	public static AgencyRequest convertToRequest(AgencyEntity entity) {
		return new AgencyRequest(
				entity.getId(), 
				entity.getPlaceId(), 
				entity.getAgencyNumber());
	}

}
