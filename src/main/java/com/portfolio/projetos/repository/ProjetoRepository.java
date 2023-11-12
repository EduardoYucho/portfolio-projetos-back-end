package com.portfolio.projetos.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.entities.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
	List<Projeto> findAll();

	Optional<Projeto> findByStatusIgnoreCase(String status);

	List<Projeto> findByNomeContainingIgnoreCase(String nome);

	@Transactional
	@Modifying
	@Query("UPDATE Projeto p SET p.membros = :membros WHERE p.id = :projetoId")
	void associarMembroAoProjeto(Long projetoId, List<Pessoa> membros);

	@Transactional
	@Modifying
	@Query("UPDATE Projeto p SET p.status = :novoStatus WHERE p.id = :projetoId")
	void atualizarStatus(Long projetoId, String novoStatus);
}
