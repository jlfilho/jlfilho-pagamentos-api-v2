package uea.pagamentos_api.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import uea.pagamentos_api.models.Permissao;
import uea.pagamentos_api.services.PermissaoService;

@RestController
@RequestMapping("/permissaos")
public class PermissaoResource {
	
	@Autowired
	private PermissaoService permissaoService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PERMISSAO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao) {
		Permissao permissaoSalva = permissaoService.criar(permissao);
		
		URI uri = ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{codigo}").
				buildAndExpand(permissaoSalva.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(permissaoSalva);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PERMISSAO') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<List<Permissao>> listar() {
		List<Permissao> permissaos = permissaoService.listar();
		return ResponseEntity.ok().body(permissaos);
	}
	
	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PERMISSAO') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Permissao> buscarPorCodigo(@PathVariable 
			Long codigo){
		Permissao permissao = permissaoService.buscarPorCodigo(codigo);
		return ResponseEntity.ok().body(permissao);
	}
	
	@DeleteMapping(value="/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PERMISSAO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Void> excluir(@PathVariable Long codigo){
		permissaoService.excluir(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PERMISSAO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Permissao> atualizar(@PathVariable Long codigo,
			@Valid @RequestBody Permissao permissao){
		Permissao permissaoSalva = permissaoService.atualizar(codigo,
				permissao);
		return ResponseEntity.ok().body(permissaoSalva);
		
	}
	
	
	
}
