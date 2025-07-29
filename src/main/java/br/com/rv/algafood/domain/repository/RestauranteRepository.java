package br.com.rv.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rv.algafood.domain.model.Restaurante;



public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
}
