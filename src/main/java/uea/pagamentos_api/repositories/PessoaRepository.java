package uea.pagamentos_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uea.pagamentos_api.models.Pessoa;
import uea.pagamentos_api.repositories.pessoa.PessoaRepositoryQuery;

@Repository
public interface PessoaRepository  extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}