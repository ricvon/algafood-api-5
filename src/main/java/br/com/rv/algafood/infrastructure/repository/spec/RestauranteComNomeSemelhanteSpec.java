package br.com.rv.algafood.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import br.com.rv.algafood.domain.model.Restaurante;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteComNomeSemelhanteSpec implements Specification<Restaurante>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	
	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {		
		return builder.like(root.get("nome"), "%" + nome + "%");
	}

}
