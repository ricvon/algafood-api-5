package br.com.rv.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.rv.algafood.domain.model.Restaurante;
import br.com.rv.algafood.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find (String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//	Aula 5.11 - JPQL Consulta fixa, com parâmetros obrigatórios
//		var jpql = "from Restaurante where nome like :nome "
//				+"and taxaFrete between :taxaInicial and :taxaFinal";
//		return manager.createQuery(jpql, Restaurante.class)
//				.setParameter("nome", "%"+nome+"%")
//				.setParameter("taxaInicial", taxaFreteInicial)
//				.setParameter("taxaFinal", taxaFreteFinal)
//				.getResultList();

//  Aula 5.12 JPQL - Consulta dinâmica		
//		var jpql = new StringBuilder();		
//		jpql.append("from Restaurante where 0=0 ");		
//		var parametros = new HashMap<String, Object>();		
//		if (StringUtils.hasLength(nome)) {
//			jpql.append("and nome like :nome ");
//			parametros.put("nome", "%" + nome + "%");
//		}		
//		if (taxaFreteInicial != null) {
//			jpql.append("and taxaFrete >= :taxaInicial ");
//			parametros.put("taxaInicial", taxaFreteInicial);
//		}		
//		if (taxaFreteFinal != null) {
//			jpql.append("and taxaFrete <= :taxaFinal ");
//			parametros.put("taxaFinal", taxaFreteFinal);
//		}		
//		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);		
//		parametros.forEach((chave,valor)->query.setParameter(chave, valor));		
//		return query.getResultList();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		criteria.from(Restaurante.class); //semelhante à: jpql.append("from Restaurante where 0=0 ");		
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
		
}
