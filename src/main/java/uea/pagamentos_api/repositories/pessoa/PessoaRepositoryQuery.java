package uea.pagamentos_api.repositories.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uea.pagamentos_api.dto.ResumoPessoaDto;
import uea.pagamentos_api.models.Pessoa;
import uea.pagamentos_api.repositories.filters.PessoaFilter;


public interface PessoaRepositoryQuery {
	public Page<ResumoPessoaDto> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
}