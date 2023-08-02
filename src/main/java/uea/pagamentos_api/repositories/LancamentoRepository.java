package uea.pagamentos_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uea.pagamentos_api.models.Lancamento;
import uea.pagamentos_api.repositories.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends 
	JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
