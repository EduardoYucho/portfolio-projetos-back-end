package com.portfolio.projetos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.projetos.entities.MembroDTO;
import com.portfolio.projetos.services.MembroService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/membros")
public class MembroController {

	private final MembroService membroService;

	@Autowired
	public MembroController(MembroService membroService) {
		this.membroService = membroService;
	}

	@PostMapping("/adicionarMembro")
	public ResponseEntity<String> adicionarMembro(@RequestBody MembroDTO membroDTO) {
		membroService.adicionarMembro(membroDTO);
		return ResponseEntity.ok("Membro adicionado com sucesso!");
	}

	@GetMapping("/membros-projeto/{idProjeto}")
	public ResponseEntity<List<MembroDTO>> getMembrosDoProjeto(@PathVariable Long idProjeto) {
		List<MembroDTO> membros = membroService.getMembrosDoProjeto(idProjeto);
		return ResponseEntity.ok(membros);
	}

	@DeleteMapping("/remover-membro/{idPessoa}/{idProjeto}")
	public ResponseEntity<String> removerMembro(@PathVariable Long idPessoa, @PathVariable Long idProjeto) {
		membroService.removerMembroDoProjeto(idPessoa, idProjeto);
		return ResponseEntity.ok("Membro removido com sucesso!");
	}
}
