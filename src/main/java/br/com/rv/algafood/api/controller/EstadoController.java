package br.com.rv.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rv.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rv.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rv.algafood.domain.model.Estado;
import br.com.rv.algafood.domain.repository.EstadoRepository;
import br.com.rv.algafood.domain.service.CadastroEstadoService;

//GET /estados HTTP/1.1

@RestController
@RequestMapping("/estados")
//@RequestMapping(value="/estados", produces=MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@GetMapping // (produces = {MediaType.APPLICATION_JSON_VALUE,
				// MediaType.APPLICATION_XML_VALUE})//produces="application/XML"
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public estadosXmlWrapper listarXml(){
//		return new estadosXmlWrapper(estadoRepository.listar());
//	}

//	@GetMapping("/{estadoId}")
//	public estado buscar(@PathVariable("estadoId") Long id) {
//		return estadoRepository.buscar(id);
//	}

	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable("estadoId") Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		// return ResponseEntity.status(HttpStatus.OK).body(estado);//com corpo
		// return ResponseEntity.status(HttpStatus.OK).build();//sem com corpo
		// return ResponseEntity.ok(estado);//com corpo shortcut
		// return ResponseEntity.ok();//sem corpo

//		HttpHeaders headers = new HttpHeaders();//retorno um local para redirecionamento
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/estados");
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
		if (estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
		estado = cadastroEstadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estado);

	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody Estado estado) {

		Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);

		if (estadoAtual.isPresent()) {
			// estadoAtual.setNome(estado.getNome());
			BeanUtils.copyProperties(estado, estadoAtual, "id"); // copia as propriedades de estado e
																	// passa para estado atual,
																	// ignornado a cópia do id, pois ele
																	// está nulo em estado
			// a partir do terceiro parámetro, tenho os campos a serem desconsiderados na
			// cópia.

			Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());

			return ResponseEntity.ok(estadoSalvo);
		}
		return ResponseEntity.notFound().build();// 404

	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable("estadoId") Long estadoId) {
		try {
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
}
