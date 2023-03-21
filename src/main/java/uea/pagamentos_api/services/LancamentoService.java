package uea.pagamentos_api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uea.pagamentos_api.dto.ResumoLancamentoDto;
import uea.pagamentos_api.models.Categoria;
import uea.pagamentos_api.models.Lancamento;
import uea.pagamentos_api.models.Pessoa;
import uea.pagamentos_api.repositories.CategoriaRepository;
import uea.pagamentos_api.repositories.LancamentoRepository;
import uea.pagamentos_api.repositories.PessoaRepository;
import uea.pagamentos_api.repositories.filters.LancamentoFilter;
import uea.pagamentos_api.services.exceptions.PessoaInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Page<ResumoLancamentoDto> resumir(LancamentoFilter lancamentoFilter,
			Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	public Lancamento criar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findById(
				lancamento.getPessoa().getCodigo()).orElseThrow();
		if(!pessoa.isAtivo()) {
			throw new PessoaInativaException();
		}
		Categoria categoria = categoriaRepository.findById(
				lancamento.getCategoria().getCodigo()).orElseThrow();
		return lancamentoRepository.save(lancamento);
	}
	
	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	
	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = lancamentoRepository.findById(codigo).orElseThrow();
		return lancamento;
	}
	
	public void excluir(Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = lancamentoRepository.
				findById(codigo).orElseThrow();
		Pessoa pessoa = pessoaRepository.findById(
				lancamento.getPessoa().getCodigo()).orElseThrow();
		if(!pessoa.isAtivo()) {
			throw new PessoaInativaException();
		}
		Categoria categoria = categoriaRepository.findById(
				lancamento.getCategoria().getCodigo()).orElseThrow();
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	

}
