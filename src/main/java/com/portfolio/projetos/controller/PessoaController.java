package com.portfolio.projetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping
	public ResponseEntity<Page<Pessoa>> getAllPessoas(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<Pessoa> pessoas = pessoaService.getAllPessoas(page, size);
		return ResponseEntity.ok(pessoas);
	}

	@PostMapping
	public void createPessoa(@RequestBody Pessoa pessoa) {
		pessoaService.createPessoa(pessoa);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> getPersonById(@PathVariable Long id) {
		try {
			Pessoa person = pessoaService.getPersonById(id);

			if (person != null) {
				return ResponseEntity.ok(person);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> updatePerson(@PathVariable Long id, @RequestBody Pessoa updatedPerson) {
		try {
			Pessoa updated = pessoaService.updatePerson(id, updatedPerson);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
