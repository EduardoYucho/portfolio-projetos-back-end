package com.portfolio.projetos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.projetos.entities.Membro;
import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.entities.Projeto;

public interface MembroRepository extends JpaRepository<Membro, Long> {

	List<Membro> findByProjetoId(Long idProjeto);

	List<Membro> findByPessoaAndProjeto(Pessoa pessoa, Projeto projeto);

	Optional<Membro> findByPessoaIdAndProjetoId(Long idPessoa, Long idProjeto);

	boolean existsByPessoaAndProjeto(Pessoa pessoa, Projeto projeto);
}
