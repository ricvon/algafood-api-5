package br.com.rv.algafood.api.controller;

import java.util.List;

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
import br.com.rv.algafood.domain.model.Cidade;
import br.com.rv.algafood.domain.repository.CidadeRepository;
import br.com.rv.algafood.domain.service.CadastroCidadeService;

//GET /cidades HTTP/1.1

@RestController
@RequestMapping("/cidades")
//@RequestMapping(value="/cidades", produces=MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@GetMapping // (produces = {MediaType.APPLICATION_JSON_VALUE,
				// MediaType.APPLICATION_XML_VALUE})//produces="application/XML"
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public cidadesXmlWrapper listarXml(){
//		return new cidadesXmlWrapper(cidadeRepository.listar());
//	}

//	@GetMapping("/{cidadeId}")
//	public cidade buscar(@PathVariable("cidadeId") Long id) {
//		return cidadeRepository.buscar(id);
//	}

	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long id) {
		Cidade cidade = cidadeRepository.buscar(id);
		// return ResponseEntity.status(HttpStatus.OK).body(cidade);//com corpo
		// return ResponseEntity.status(HttpStatus.OK).build();//sem com corpo
		// return ResponseEntity.ok(cidade);//com corpo shortcut
		// return ResponseEntity.ok();//sem corpo

//		HttpHeaders headers = new HttpHeaders();//retorno um local para redirecionamento
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/cidades");
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
		if (cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long cidadeId,
			@RequestBody Cidade cidade) {

		try {
			Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);

			if (cidadeAtual != null) {
				// cidadeAtual.setNome(cidade.getNome());
				BeanUtils.copyProperties(cidade, cidadeAtual, "id"); // copia as propriedades de cidade e
																				// passa para cidade atual,
																				// ignornado a cópia do id, pois ele
																				// está nulo em cidade
				// a partir do terceiro parámetro, tenho os campos a serem desconsiderados na
				// cópia.

				cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

				return ResponseEntity.ok(cidadeAtual);
			}
			return ResponseEntity.notFound().build();// 404

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}

	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable("cidadeId") Long cidadeId) {
		try {
			cadastroCidadeService.excluir(cidadeId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
}
