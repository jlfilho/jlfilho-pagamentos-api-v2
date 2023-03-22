package uea.pagamentos_api.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import uea.pagamentos_api.models.Usuario;
import uea.pagamentos_api.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalva = usuarioService.criar(usuario);
		
		URI uri = ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{codigo}").
				buildAndExpand(usuarioSalva.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(usuarioSalva);
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuarios = usuarioService.listar();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Usuario> buscarPorCodigo(@PathVariable 
			Long codigo){
		Usuario usuario = usuarioService.buscarPorCodigo(codigo);
		return ResponseEntity.ok().body(usuario);
	}
	
	@DeleteMapping(value="/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable Long codigo){
		usuarioService.excluir(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{codigo}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo,
			@Valid @RequestBody Usuario usuario){
		Usuario usuarioSalva = usuarioService.atualizar(codigo,
				usuario);
		return ResponseEntity.ok().body(usuarioSalva);
		
	}	
	
}
