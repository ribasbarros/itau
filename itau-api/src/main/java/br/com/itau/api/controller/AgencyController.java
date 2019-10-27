package br.com.itau.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.itau.api.controller.convert.AgencyHelper;
import br.com.itau.api.request.AgencyRequest;
import br.com.itau.api.response.AgencyResponse;
import br.com.itau.domain.entity.AgencyEntity;
import br.com.itau.helper.ExceptionHelper;
import br.com.itau.helper.ReturnMessage;
import br.com.itau.service.AgencyService;
import br.com.itau.service.exceptions.AgencyNotFoundException;

@Controller
@RequestMapping("/api/agency")
@CrossOrigin("http://localhost:3000")
public class AgencyController {

	private static final String DELETED_WITH_SUCCESS = "Deleted with success!";

	private static final String UPDATED_WITH_SUCCESS = "Updated with success!";

	private static final String AGENCY_NOT_FOUND = "Agency not found!";

	private static final String PARAMETER_IS_INVALID = "Parameter is invalid!";

	private static final String CREATED_WITH_SUCCESS = "Created with success!";

	private static final String FIND_WITH_SUCCESS = "Finded with success!";

	@Autowired
	private AgencyService service;

	@RequestMapping(value = "/v1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ReturnMessage<AgencyResponse>> create(@RequestBody AgencyRequest request) {

		ReturnMessage<AgencyResponse> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			AgencyEntity agencyEntity = AgencyHelper.convert(request);
			agencyEntity = service.create(agencyEntity);

			restResponse.setResult(AgencyHelper.convert(agencyEntity));
			restResponse.setHttpMensagem(CREATED_WITH_SUCCESS);

		} catch (Exception e) {

			status = HttpStatus.BAD_REQUEST;
			restResponse.setHttpMensagem(PARAMETER_IS_INVALID);
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		}

		return new ResponseEntity<>(restResponse, status);
	}

	@RequestMapping(value = "/v1/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Page<AgencyEntity> search(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		return service.search(page, size);

	}

	@RequestMapping(value = "/v1/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ReturnMessage<AgencyResponse>> find(@PathVariable Long id) {

		ReturnMessage<AgencyResponse> restResponse = new ReturnMessage<AgencyResponse>();
		HttpStatus status = HttpStatus.OK;

		try {
			AgencyEntity agencyEntity = service.find(id);

			restResponse.setResult(AgencyHelper.convert(agencyEntity));
			restResponse.setHttpMensagem(FIND_WITH_SUCCESS);

		} catch (AgencyNotFoundException e) {

			status = HttpStatus.NOT_FOUND;
			restResponse.setHttpMensagem(AGENCY_NOT_FOUND);
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		} catch (Exception e) {

			status = HttpStatus.BAD_REQUEST;
			restResponse.setHttpMensagem(PARAMETER_IS_INVALID);
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		}

		return new ResponseEntity<>(restResponse, status);
	}

	@RequestMapping(value = "/v1/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ReturnMessage<Boolean>> update(@PathVariable(value = "id") Long id,
			@RequestBody AgencyRequest request) {

		ReturnMessage<Boolean> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.OK;

		try {

			service.update(id, AgencyHelper.convert(request));

			restResponse.setResult(Boolean.TRUE);
			restResponse.setHttpMensagem(UPDATED_WITH_SUCCESS);

		} catch (AgencyNotFoundException e) {

			status = HttpStatus.NOT_FOUND;
			restResponse.setResult(Boolean.FALSE);
			restResponse.setHttpMensagem(AGENCY_NOT_FOUND);
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		}

		return new ResponseEntity<>(restResponse, status);

	}

	@RequestMapping(value = "/v1/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ReturnMessage<Boolean>> delete(@PathVariable Long id) {
		ReturnMessage<Boolean> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.OK;

		try {

			service.delete(id);

			restResponse.setResult(Boolean.TRUE);
			restResponse.setHttpMensagem(DELETED_WITH_SUCCESS);

		} catch (AgencyNotFoundException e) {

			status = HttpStatus.NOT_FOUND;
			restResponse.setResult(Boolean.FALSE);
			restResponse.setHttpMensagem(AGENCY_NOT_FOUND);
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		}

		return new ResponseEntity<>(restResponse, status);

	}

}
