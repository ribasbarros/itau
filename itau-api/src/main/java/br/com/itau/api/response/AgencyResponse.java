package br.com.itau.api.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyResponse implements Serializable {

	private static final long serialVersionUID = 5593587161961292082L;

	private Long id;

	private String placeId;

	private Integer agencyNumber;

	private Date dtCreation;

}
