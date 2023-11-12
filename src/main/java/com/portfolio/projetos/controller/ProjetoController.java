package com.portfolio.projetos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.projetos.entities.Projeto;
import com.portfolio.projetos.exception.PessoaNotFoundException;
import com.portfolio.projetos.exception.ProjetoNotFoundException;
import com.portfolio.projetos.exception.StatusInvalidoException;
import com.portfolio.projetos.services.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	private final ProjetoService projetoService;

	@Autowired
	public ProjetoController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	@GetMapping
	public List<Projeto> getAllProjetos() {
		return projetoService.getAllProjetos();
	}

	@GetMapping("/{id}")
	public Optional<Projeto> getProjetoById(@PathVariable Long id) {
		return projetoService.getProjetoById(id);
	}

	@PostMapping
	public ResponseEntity<String> createProjeto(@RequestBody Projeto projeto) {
		projetoService.createProjeto(projeto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Projeto criado com sucesso!");
	}

	@PutMapping("/atualizar-projeto/{id}")
	public ResponseEntity<String> updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
		try {
			projetoService.updateProjeto(id, projeto);
			return ResponseEntity.ok("Projeto atualizado com sucesso!");
		} catch (ProjetoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProjeto(@PathVariable Long id) {
		try {
			projetoService.deleteProjeto(id);
			return ResponseEntity.ok("Projeto excluído com sucesso!");
		} catch (ProjetoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
		}
	}

	@PostMapping("/{id}/associar-membro/{idMembro}")
	public ResponseEntity<String> associarMembroAoProjeto(@PathVariable Long id, @PathVariable Long idMembro) {
		try {
			projetoService.associarMembroAoProjeto(id, idMembro);
			return ResponseEntity.ok("Membro associado ao projeto com sucesso!");
		} catch (ProjetoNotFoundException | PessoaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto ou membro não encontrado.");
		}
	}

	@GetMapping("/detalhes/{id}")
	public ResponseEntity<?> obterDetalhesProjeto(@PathVariable Long id) {
		try {
			Optional<Projeto> projeto = projetoService.getProjetoById(id);

			if (projeto.isPresent()) {
				return ResponseEntity.ok().body(projeto.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Projeto não encontrado.\"}");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\":\"Erro ao obter detalhes do projeto.\"}");
		}
	}

	@PutMapping("/{projetoId}/atualizar-status")
	public ResponseEntity<?> atualizarStatusProjeto(@PathVariable Long projetoId, @RequestBody String novoStatus) {
		try {
			projetoService.atualizarStatus(projetoId, novoStatus);
			return ResponseEntity.ok().body("{\"mensagem\":\"Status atualizado com sucesso.\"}");
		} catch (ProjetoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Projeto não encontrado.\"}");
		} catch (StatusInvalidoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Status inválido.\"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\":\"Erro ao atualizar o status do projeto.\"}");
		}
	}
}
