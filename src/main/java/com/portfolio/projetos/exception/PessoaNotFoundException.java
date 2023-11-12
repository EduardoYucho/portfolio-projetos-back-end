package com.portfolio.projetos.exception;

public class PessoaNotFoundException extends RuntimeException {

	public PessoaNotFoundException() {
		super();
	}

	public PessoaNotFoundException(String message) {
		super(message);
	}
}
