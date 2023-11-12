package com.portfolio.projetos.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "membros")
public class Membro {

	@Id
	@SequenceGenerator(name = "id_membros_seq_new", sequenceName = "id_membros_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_membros_seq_new")
	public Long id;

	@ManyToOne
	@JoinColumn(name = "idpessoa")
	@JsonIgnore
	private Pessoa pessoa;

	@ManyToOne
	@JoinColumn(name = "idprojeto")
	@JsonIgnore
	private Projeto projeto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Membro() {

	}

	public Membro(Pessoa pessoa, Projeto projeto) {
		this.pessoa = pessoa;
		this.projeto = projeto;
	}

}
