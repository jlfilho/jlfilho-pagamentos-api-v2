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
import uea.pagamentos_api.models.Pessoa;
import uea.pagamentos_api.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.criar(pessoa);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(pessoaSalva);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<List<Pessoa>> listar() {
		List<Pessoa> pessoas = pessoaService.listar();
		return ResponseEntity.ok().body(pessoas);
	}

	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.buscarPorCodigo(codigo);
		return ResponseEntity.ok().body(pessoa);
	}

	@DeleteMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Void> excluir(@PathVariable Long codigo) {
		pessoaService.excluir(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{codigo}/ativo")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> atualizarPropriedadeAtivo(
			@PathVariable Long codigo, @RequestBody Boolean ativo){
		Pessoa pessoaSalva = pessoaService.
				atualizarPropriedadeAtivo(codigo, ativo);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok().body(pessoaSalva);

	}

}
