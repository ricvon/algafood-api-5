package br.com.rv.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rv.algafood.domain.model.Restaurante;


@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id") --Removido e acrescentado no arquvio resorucer/META-INF/orm.xml
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	//List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	List<Restaurante> findTop2ByNomeContaining(String nome);
	int countByCozinhaId(Long cozinha);
	
	public List<Restaurante> find (String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxafreteFinal);	

	
}
