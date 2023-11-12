package com.portfolio.projetos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.portfolio.projetos.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findByNome(String nome);

	@Query("SELECT p FROM Pessoa p WHERE p.nome LIKE %:keyword%")
	List<Pessoa> searchByKeyword(@Param("keyword") String keyword);

	Page<Pessoa> findAll(Pageable pageable);

	Optional<Pessoa> findById(Long id);

}
