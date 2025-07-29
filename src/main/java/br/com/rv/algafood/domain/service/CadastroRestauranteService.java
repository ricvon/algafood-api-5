package br.com.rv.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rv.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rv.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rv.algafood.domain.model.Cozinha;
import br.com.rv.algafood.domain.model.Restaurante;
import br.com.rv.algafood.domain.repository.CozinhaRepository;
import br.com.rv.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()->new EntidadeNaoEncontradaException(String.format("Não Existe cadastro de cozinha com código %d", cozinhaId)));
		
//		if (cozinha.isEmpty()) {
//			throw new EntidadeNaoEncontradaException(String.format("Não Existe cadastro de cozinha com código %d", cozinhaId));
//		}
		
		restaurante.setCozinha(cozinha);
		
		//incluiria as regras de negócio
		return restauranteRepository.save(restaurante);
	}
	
	public void excluir (Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Restaurante com código %d", restauranteId));
		}catch (DataIntegrityViolationException e){
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, pois está em uso", restauranteId));
		}
	}

}
