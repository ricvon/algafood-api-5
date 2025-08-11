package br.com.rv.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.rv.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}