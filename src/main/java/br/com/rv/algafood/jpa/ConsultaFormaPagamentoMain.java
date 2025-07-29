package br.com.rv.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rv.algafood.AlgafoodApi5Application;
import br.com.rv.algafood.domain.model.FormaPagamento;
import br.com.rv.algafood.domain.repository.FormaPagamentoRepository;


public class ConsultaFormaPagamentoMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApi5Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
		for (FormaPagamento formaPagamento : formaPagamentos) {
			System.out.printf("%s - %s\n", formaPagamento.getId(), formaPagamento.getDescricao());
		}
	}
}
