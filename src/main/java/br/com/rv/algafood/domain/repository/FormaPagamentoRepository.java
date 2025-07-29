package br.com.rv.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rv.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository  extends JpaRepository<FormaPagamento, Long>{
}
