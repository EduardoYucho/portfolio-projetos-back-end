package com.portfolio.projetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.projetos.repository.PessoaRepository;

@RestController
@RequestMapping("/api")
public class GerenteController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping("/verificar-gerente/{gerenteId}")
	public ResponseEntity<?> verificarGerente(@PathVariable Long gerenteId) {
		try {
			boolean isGerenteValido = pessoaRepository.existsById(gerenteId);

			return ResponseEntity.ok().body("{\"isGerenteValido\":" + isGerenteValido + "}");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\":\"Erro ao verificar o gerente.\"}");
		}
	}
}
