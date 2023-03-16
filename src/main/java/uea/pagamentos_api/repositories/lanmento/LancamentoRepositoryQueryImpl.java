package uea.pagamentos_api.repositories.lanmento;

import java.util.List;

import uea.pagamentos_api.dto.ResumoLancamentoDto;
import uea.pagamentos_api.repositories.filters.LancamentoFilter;

public class LancamentoRepositoryQueryImpl 
implements LancamentoRepositoryQuery{

	@Override
	public List<ResumoLancamentoDto> filtrar(LancamentoFilter lancamentoFilter) {
		
		return null;
	}

}
