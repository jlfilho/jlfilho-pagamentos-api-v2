package uea.pagamentos_api.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import uea.pagamentos_api.dto.ResumoLancamentoDto;
import uea.pagamentos_api.models.Lancamento;
import uea.pagamentos_api.repositories.filters.LancamentoFilter;
import uea.pagamentos_api.services.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento) {
		Lancamento lancamentoSalva = lancamentoService.criar(lancamento);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(lancamentoSalva.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(lancamentoSalva);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Page<ResumoLancamentoDto>> resumir(LancamentoFilter lancamentoFilter,
			Pageable pageable) {
		Page<ResumoLancamentoDto> resumos = lancamentoService.resumir(lancamentoFilter, pageable);
		return ResponseEntity.ok().body(resumos);
	}

	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoService.buscarPorCodigo(codigo);
		return ResponseEntity.ok().body(lancamento);
	}

	@DeleteMapping(value = "/{codigo}")
	@PreAuthorize("hasRole('ROLE_REMOVER_LANCAMENTO')")
	public ResponseEntity<Void> excluir(@PathVariable Long codigo) {
		lancamentoService.excluir(codigo);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
		Lancamento lancamentoSalva = lancamentoService.atualizar(codigo, lancamento);
		return ResponseEntity.ok().body(lancamentoSalva);

	}

}
