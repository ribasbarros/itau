package br.com.itau.api.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectRequest implements Serializable {
	
	private static final long serialVersionUID = -5973016897604194111L;
	
	private Double lat;
	
	private Double lng;
	
	@JsonIgnore
	private String browserDevice;
	
	private String placeId;

}
