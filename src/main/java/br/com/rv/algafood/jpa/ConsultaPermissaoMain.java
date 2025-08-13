package br.com.rv.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rv.algafood.AlgafoodApi6Application;
import br.com.rv.algafood.domain.model.Permissao;
import br.com.rv.algafood.domain.repository.PermissaoRepository;


public class ConsultaPermissaoMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApi6Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
		List<Permissao> permissoes = permissaoRepository.findAll();
		for (Permissao permissao : permissoes) {
			System.out.printf("%s - %s\n", permissao.getId(), permissao.getNome());
		}
	}
}
