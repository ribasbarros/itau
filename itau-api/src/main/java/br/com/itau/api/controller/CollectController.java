package br.com.itau.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.itau.api.controller.convert.CollectHelper;
import br.com.itau.api.request.CollectRequest;
import br.com.itau.api.response.CollectResponse;
import br.com.itau.domain.entity.CollectEntity;
import br.com.itau.helper.ExceptionHelper;
import br.com.itau.helper.ReturnMessage;
import br.com.itau.service.AgencyService;
import br.com.itau.service.CollectService;
import br.com.itau.service.exceptions.AgencyNotFoundException;
import br.com.itau.service.exceptions.InvalidParameterException;

@Controller
@CrossOrigin
@RequestMapping("/api/collect")
public class CollectController {

	private static final String HEADER_USER_AGENT = "User-Agent";

	private static final String PARAMETER_IS_INVALID = "Parameter is invalid!";

	private static final String AGENCY_NOT_FOUND = "Agency not found!";

	private static final String CREATED_WITH_SUCCESS = "Created with success!";

	@Autowired
	private CollectService collectService;
	
	@Autowired
	private AgencyService agencyService;
	
	@RequestMapping(value = "/v1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ReturnMessage<CollectResponse>> collect(@RequestBody CollectRequest collectRequest, HttpServletRequest request){
		
		ReturnMessage<CollectResponse> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			//Browser and Device
			//sample 1: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36
			//sample 2: Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Mobile Safari/537.36
			//sample 3: Mozilla/5.0 (Linux; Android 9; SAMSUNG SM-A305GT) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/10.1 Chrome/71.0.3578.99 Mobile Safari/537.36
			collectRequest.setBrowserDevice(request.getHeader(HEADER_USER_AGENT));
			CollectEntity collectEntity = CollectHelper.convert(collectRequest);
			collectEntity.setAgency(agencyService.findByPlaceId(collectRequest.getPlaceId()));
			
			CollectResponse response = CollectHelper.convert(collectService.create(collectEntity));
			
			restResponse.setResult(response);
			restResponse.setHttpMensagem(CREATED_WITH_SUCCESS);
			
		} catch (AgencyNotFoundException e) {
			
			status = HttpStatus.NOT_FOUND;
			restResponse.setHttpMensagem(AGENCY_NOT_FOUND);
			restResponse.setDetalhe(ExceptionHelper.toString(e));
			
			
		} catch (InvalidParameterException e) {
			
			status = HttpStatus.BAD_REQUEST;
			restResponse.setHttpMensagem(PARAMETER_IS_INVALID);
			restResponse.setDetalhe(ExceptionHelper.toString(e));
			
		}
		
		return new ResponseEntity<>(restResponse, status);
	}
}
