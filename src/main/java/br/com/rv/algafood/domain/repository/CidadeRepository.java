package br.com.rv.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rv.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
}
