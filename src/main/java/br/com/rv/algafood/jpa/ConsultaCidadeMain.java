package br.com.rv.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rv.algafood.AlgafoodApi5Application;
import br.com.rv.algafood.domain.model.Cidade;
import br.com.rv.algafood.domain.repository.CidadeRepository;


public class ConsultaCidadeMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApi5Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
		CidadeRepository CidadeRepository = applicationContext.getBean(CidadeRepository.class);
		List<Cidade> cidades = CidadeRepository.findAll();
		for (Cidade cidade : cidades) {
			System.out.printf("%s - %s - %s\n", cidade.getId(), cidade.getNome(), cidade.getEstado().getNome());
		}
	}
}
