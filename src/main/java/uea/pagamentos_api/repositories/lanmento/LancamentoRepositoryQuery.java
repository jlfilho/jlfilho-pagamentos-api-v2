package uea.pagamentos_api.repositories.lanmento;

import java.util.List;

import uea.pagamentos_api.dto.ResumoLancamentoDto;
import uea.pagamentos_api.repositories.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<ResumoLancamentoDto> filtrar(
			LancamentoFilter lancamentoFilter);

}
