package com.portfolio.projetos.exception;

public class ProjetoNotFoundException extends RuntimeException {

	public ProjetoNotFoundException() {
		super();
	}

	public ProjetoNotFoundException(String message) {
		super(message);
	}
}
