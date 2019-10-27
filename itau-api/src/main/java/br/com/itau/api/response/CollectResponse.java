package br.com.itau.api.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectResponse implements Serializable {

	private static final long serialVersionUID = -3453855347211002748L;

	private Long id;

	private Integer agency;

}
