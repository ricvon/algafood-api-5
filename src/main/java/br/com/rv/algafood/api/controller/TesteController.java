package br.com.rv.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rv.algafood.domain.model.Cozinha;
import br.com.rv.algafood.domain.model.Restaurante;
import br.com.rv.algafood.domain.repository.CozinhaRepository;
import br.com.rv.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
		return cozinhaRepository.findTodasByNomeContaining(nome);	
	}

	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome){
		return cozinhaRepository.findByNome(nome);	
	}

	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(@RequestParam("taxaInicial") BigDecimal taxaInicial, @RequestParam("taxaFinal")  BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
		
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(@RequestParam("nome") String nome, Long cozinhaId){
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
	}
}
