package br.com.rv.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rv.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rv.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rv.algafood.domain.model.Estado;
import br.com.rv.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public Estado salvar(Estado estado) {
		
		//incluiria as regras de negócio
		return estadoRepository.save(estado);
	}
	
	public void excluir (Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Estado com código %d", estadoId));
		}catch (DataIntegrityViolationException e){
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois está em uso", estadoId));
		}
	}

}
