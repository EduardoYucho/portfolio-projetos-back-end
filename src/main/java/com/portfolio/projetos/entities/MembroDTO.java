package com.portfolio.projetos.entities;

import javax.persistence.Table;

@Table(name = "membros")
public class MembroDTO {

	private Long idPessoa;
	private Long idProjeto;
	private String nomePessoa;

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public MembroDTO(Long pessoa, Long projeto) {
		this.setIdPessoa(pessoa);
		this.setIdProjeto(projeto);
	}

	public MembroDTO() {
	}

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
}
