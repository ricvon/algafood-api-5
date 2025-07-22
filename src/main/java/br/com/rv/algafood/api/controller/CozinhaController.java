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
import br.com.rv.algafood.domain.model.Cozinha;
import br.com.rv.algafood.domain.repository.CozinhaRepository;
import br.com.rv.algafood.domain.service.CadastroCozinhaService;

//GET /cozinhas HTTP/1.1

@RestController
@RequestMapping("/cozinhas")
//@RequestMapping(value="/cozinhas", produces=MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@GetMapping //(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})//produces="application/XML"
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasXmlWrapper listarXml(){
//		return new CozinhasXmlWrapper(cozinhaRepository.listar());
//	}
	
//	@GetMapping("/{cozinhaId}")
//	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
//		return cozinhaRepository.buscar(id);
//	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);//com corpo
		//return ResponseEntity.status(HttpStatus.OK).build();//sem com corpo
		//return ResponseEntity.ok(cozinha);//com corpo shortcut
		//return ResponseEntity.ok();//sem corpo
		
//		HttpHeaders headers = new HttpHeaders();//retorno um local para redirecionamento
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/cozinhas");
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
		if (cozinha!=null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha Adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}	

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar (@PathVariable("cozinhaId") Long cozinhaId, 
			@RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinhaAtual != null) {
			//cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); //cópia as propriedades de cozinha e passa para cozinha atual, ignornado a cópia do id, pois ele está nulo em cozinha 
			//a partir do terceiro parámetro, tenho os campos a serem desconsiderados na cópia.
			
			cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
			
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover (@PathVariable("cozinhaId") Long cozinhaId){
		try {
			cadastroCozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
}
