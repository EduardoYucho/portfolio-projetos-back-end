package com.portfolio.projetos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	public Page<Pessoa> getAllPessoas(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return pessoaRepository.findAll(pageable);
	}

	public void createPessoa(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

	public Pessoa getPersonById(Long id) {
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
		return pessoaOptional.orElse(null);
	}

	public Pessoa updatePerson(Long id, Pessoa updatedPerson) {
		Optional<Pessoa> existingPersonOptional = pessoaRepository.findById(id);

		if (existingPersonOptional.isPresent()) {
			Pessoa existingPerson = existingPersonOptional.get();

			existingPerson.setNome(updatedPerson.getNome());
			existingPerson.setDataNascimento(updatedPerson.getDataNascimento());
			existingPerson.setCpf(updatedPerson.getCpf());
			existingPerson.setFuncionario(updatedPerson.isFuncionario());

			return pessoaRepository.save(existingPerson);
		} else {
			return null;
		}
	}

}
