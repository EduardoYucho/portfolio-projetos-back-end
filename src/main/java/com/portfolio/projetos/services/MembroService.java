package com.portfolio.projetos.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.projetos.entities.Membro;
import com.portfolio.projetos.entities.MembroDTO;
import com.portfolio.projetos.entities.Pessoa;
import com.portfolio.projetos.entities.Projeto;
import com.portfolio.projetos.repository.MembroRepository;
import com.portfolio.projetos.repository.PessoaRepository;
import com.portfolio.projetos.repository.ProjetoRepository;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ProjetoRepository projetoRepository;

	public void adicionarMembro(MembroDTO membro) {
		Pessoa pessoa = pessoaRepository.findById(membro.getIdPessoa()).orElseThrow(
				() -> new EntityNotFoundException("Pessoa não encontrada com o ID: " + membro.getIdPessoa()));

		Projeto projeto = projetoRepository.findById(membro.getIdProjeto()).orElseThrow(
				() -> new EntityNotFoundException("Projeto não encontrado com o ID: " + membro.getIdProjeto()));

		if (!pessoa.isFuncionario()) {
			throw new IllegalArgumentException(
					"A pessoa não é um funcionário e não pode ser adicionada como membro do projeto.");
		}

		if (membroRepository.existsByPessoaAndProjeto(pessoa, projeto)) {
			throw new IllegalStateException("A pessoa já é um membro deste projeto.");
		}

		Membro novoMembro = new Membro(pessoa, projeto);
		membroRepository.save(novoMembro);
	}

	public List<MembroDTO> getMembrosDoProjeto(Long idProjeto) {
		List<Membro> membros = membroRepository.findByProjetoId(idProjeto);
		return converterParaDTO(membros);
	}

	public List<MembroDTO> converterParaDTO(List<Membro> membros) {
		return membros.stream().map(this::converterParaDTO).collect(Collectors.toList());
	}

	public MembroDTO converterParaDTO(Membro membro) {
		MembroDTO membroDTO = new MembroDTO();
		membroDTO.setIdPessoa(membro.getPessoa().getId());
		membroDTO.setNomePessoa(membro.getPessoa().getNome());
		membroDTO.setIdProjeto(membro.getProjeto().getId());
		return membroDTO;
	}

	public void removerMembroDoProjeto(Long idPessoa, Long idProjeto) {
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElse(null);
		Projeto projeto = projetoRepository.findById(idProjeto).orElse(null);

		if (pessoa != null && projeto != null) {
			List<Membro> membros = membroRepository.findByPessoaAndProjeto(pessoa, projeto);

			if (!membros.isEmpty()) {
				membroRepository.deleteAll(membros);
			}
		}
	}

}
