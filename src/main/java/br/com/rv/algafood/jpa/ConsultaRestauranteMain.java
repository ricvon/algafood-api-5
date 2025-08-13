package br.com.rv.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rv.algafood.AlgafoodApi6Application;
import br.com.rv.algafood.domain.model.Restaurante;
import br.com.rv.algafood.domain.repository.RestauranteRepository;


public class ConsultaRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApi6Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		List<Restaurante> Restaurantes = restauranteRepository.findAll();
		for (Restaurante restaurante : Restaurantes) {
			System.out.printf("%s - %f - %s\n", restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
		}
	}
}
