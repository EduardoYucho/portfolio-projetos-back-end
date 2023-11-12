package com.portfolio.projetos.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.projetos.entities.Membro;
import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.entities.Projeto;
import com.portfolio.projetos.exception.PessoaNotFoundException;
import com.portfolio.projetos.exception.ProjetoNotFoundException;
import com.portfolio.projetos.exception.StatusInvalidoException;
import com.portfolio.projetos.repository.PessoaRepository;
import com.portfolio.projetos.repository.ProjetoRepository;

@Service
public class ProjetoService {

	private final ProjetoRepository projetoRepository;
	private final PessoaRepository pessoaRepository;

	@Autowired
	public ProjetoService(ProjetoRepository projetoRepository, PessoaRepository pessoaRepository) {
		this.projetoRepository = projetoRepository;
		this.pessoaRepository = pessoaRepository;
	}

	public List<Projeto> getAllProjetos() {
		return projetoRepository.findAll();
	}

	public Optional<Projeto> getProjetoById(Long id) {
		return projetoRepository.findById(id);
	}

	public void createProjeto(Projeto projeto) {
		projetoRepository.save(projeto);
	}

	public void updateProjeto(Long id, Projeto projeto) {
		Projeto existingProjeto = projetoRepository.findById(id)
				.orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado."));

		existingProjeto.setNome(projeto.getNome());
		existingProjeto.setDataInicio(projeto.getDataInicio());
		existingProjeto.setDataPrevisaoFim(projeto.getDataPrevisaoFim());
		existingProjeto.setDataFim(projeto.getDataFim());
		existingProjeto.setDescricao(projeto.getDescricao());
		existingProjeto.setStatus(projeto.getStatus());
		existingProjeto.setOrcamento(projeto.getOrcamento());
		existingProjeto.setClassificacaoRisco(projeto.getClassificacaoRisco());
		existingProjeto.setGerente(projeto.getGerente());

		projetoRepository.save(existingProjeto);
	}

	public void deleteProjeto(Long id) {
		projetoRepository.findById(id).ifPresentOrElse(projeto -> projetoRepository.delete(projeto),
				ProjetoNotFoundException::new);
	}

	public void associarMembroAoProjeto(Long idProjeto, Long idMembro) {
		Projeto projeto = projetoRepository.findById(idProjeto).orElseThrow(ProjetoNotFoundException::new);

		Pessoa pessoa = pessoaRepository.findById(idMembro).filter(Pessoa::isFuncionario)
				.orElseThrow(PessoaNotFoundException::new);

		Membro novoMembro = new Membro(pessoa, projeto);

		projeto.getMembros().add(novoMembro);
		projetoRepository.save(projeto);
	}

	public Projeto obterProjeto(Long id) {
		return projetoRepository.findById(id).orElseThrow(ProjetoNotFoundException::new);
	}

	public void atualizarStatus(Long id, String novoStatus) {
		Projeto projeto = projetoRepository.findById(id).orElseThrow(ProjetoNotFoundException::new);
		validarStatus(novoStatus);

		projeto.setStatus(novoStatus);
		projetoRepository.save(projeto);
	}

	private void validarStatus(String status) {
		List<String> statusValidos = Arrays.asList("Em Análise", "Análise Realizada", "Análise Aprovada", "Iniciado",
				"Planejado", "Em Andamento", "Encerrado", "Cancelado");

		if (!statusValidos.contains(status.toLowerCase())) {
			throw new StatusInvalidoException("Status inválido: " + status);
		}
	}
}
