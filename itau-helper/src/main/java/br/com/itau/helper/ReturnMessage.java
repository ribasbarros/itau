package br.com.itau.helper;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnMessage<T> implements Serializable {

	private static final long serialVersionUID = -3274696222288439546L;
	
	private String httpMensagem;
	private T result;
	private String detalhe;

}
