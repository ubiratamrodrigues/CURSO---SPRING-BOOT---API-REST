package com.example.algamoney.api.resource;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

//Controlador, recebe requisições

@RestController //retorna convertendo pra Json
@RequestMapping("/categorias") //mapeia requisições
public class CategoriaResource {

	@Autowired // essa anotação busca a implementação CategoriaRepositorio e injenta na variável
	private CategoriaRepository categoriaRepository;
	
	@GetMapping // recurso GET traz a lista de pesquisa
	public List<Categoria> listar(){
		return categoriaRepository.findAll(); //nesse método está implementado o select * from...
	}
	
	@PostMapping // salva no banco de dados usando o recurso POST
	public ResponseEntity<Categoria> criar(@Valid@ RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		 Categoria categoria = categoriaRepository.findOne(codigo);
		 return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
   }
}
