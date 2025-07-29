package br.com.rv.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rv.algafood.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
}
