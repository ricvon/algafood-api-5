package br.com.rv.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.rv.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass=CustomJpaRepositoryImpl.class)
public class AlgafoodApi6Application {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApi6Application.class, args);
	}

}
