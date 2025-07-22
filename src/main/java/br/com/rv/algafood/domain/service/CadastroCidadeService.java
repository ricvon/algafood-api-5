package br.com.rv.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rv.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rv.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rv.algafood.domain.model.Cidade;
import br.com.rv.algafood.domain.model.Estado;
import br.com.rv.algafood.domain.repository.CidadeRepository;
import br.com.rv.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não Existe cadastro de estado com código %d", estadoId));
		}
		
		cidade.setEstado(estado);
		
		//incluiria as regras de negócio
		return cidadeRepository.salvar(cidade);
	}
	
	public void excluir (Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Cidade com código %d", cidadeId));
		}catch (DataIntegrityViolationException e){
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}

}
