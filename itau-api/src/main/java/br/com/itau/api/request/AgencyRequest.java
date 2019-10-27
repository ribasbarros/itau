package br.com.itau.api.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyRequest implements Serializable {

	private static final long serialVersionUID = -3538827014189023524L;

	private Long id;

	private String placeId;

	private Integer agencyNumber;

}
