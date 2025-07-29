package br.com.rv.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rv.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rv.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rv.algafood.domain.model.Restaurante;
import br.com.rv.algafood.domain.repository.RestauranteRepository;
import br.com.rv.algafood.domain.service.CadastroRestauranteService;

//GET /restaurantes HTTP/1.1

@RestController
@RequestMapping("/restaurantes")
//@RequestMapping(value="/restaurantes", produces=MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping // (produces = {MediaType.APPLICATION_JSON_VALUE,
				// MediaType.APPLICATION_XML_VALUE})//produces="application/XML"
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public restaurantesXmlWrapper listarXml(){
//		return new restaurantesXmlWrapper(restauranteRepository.listar());
//	}

//	@GetMapping("/{restauranteId}")
//	public restaurante buscar(@PathVariable("restauranteId") Long id) {
//		return restauranteRepository.buscar(id);
//	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		// return ResponseEntity.status(HttpStatus.OK).body(restaurante);//com corpo
		// return ResponseEntity.status(HttpStatus.OK).build();//sem com corpo
		// return ResponseEntity.ok(restaurante);//com corpo shortcut
		// return ResponseEntity.ok();//sem corpo

//		HttpHeaders headers = new HttpHeaders();//retorno um local para redirecionamento
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/restaurantes");
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Restaurante restaurante) {

		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

			if (restauranteAtual.isPresent()) {
				// restauranteAtual.setNome(restaurante.getNome());
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id"); // copia as propriedades de restaurante e
																				// passa para restaurante atual,
																				// ignornado a cópia do id, pois ele
																				// está nulo em restaurante
				// a partir do terceiro parámetro, tenho os campos a serem desconsiderados na
				// cópia.

				Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual.get());

				return ResponseEntity.ok(restauranteSalvo);
			}
			return ResponseEntity.notFound().build();// 404

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable("restauranteId") Long restauranteId) {
		try {
			cadastroRestauranteService.excluir(restauranteId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Map<String, Object> campos) {

		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();// 404
		}

		merge(campos, restauranteAtual.get());

		return atualizar(restauranteId, restauranteAtual.get());
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = org.springframework.util.ReflectionUtils.findField(Restaurante.class, nomePropriedade);					
			field.setAccessible(true);//para poder acessar o atributo privado		
			Object novoValor = org.springframework.util.ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}

}
