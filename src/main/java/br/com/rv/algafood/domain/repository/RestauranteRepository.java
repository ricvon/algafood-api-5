package br.com.rv.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rv.algafood.domain.model.Restaurante;



public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
}
