package uea.pagamentos_api.repositories.lancamento;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uea.pagamentos_api.dto.ResumoLancamentoDto;
import uea.pagamentos_api.repositories.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<ResumoLancamentoDto> filtrar(
			LancamentoFilter lancamentoFilter, Pageable pageable);

}
