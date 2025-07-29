package br.com.rv.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rv.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{	
	List<Cozinha> findTodasByNome(String nome);//eu posso colocar findby<campo> ou apenas o <campo> ou find<qualquer coisa>by<campo>
	Optional<Cozinha> findByNome(String nome);//eu posso colocar findby<campo> ou apenas o <campo> ou find<qualquer coisa>by<campo>
}
