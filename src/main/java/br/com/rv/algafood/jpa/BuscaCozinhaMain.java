package br.com.rv.algafood.jpa;

import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rv.algafood.AlgafoodApi6Application;
import br.com.rv.algafood.domain.model.Cozinha;
import br.com.rv.algafood.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApi6Application.class)
				.web(WebApplicationType.NONE).run(args);
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		Optional<Cozinha> cozinha = cozinhaRepository.findById(1L);
		System.out.println(cozinha.get().getNome());

	}
}
