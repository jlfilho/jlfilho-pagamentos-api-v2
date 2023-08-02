package uea.pagamentos_api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uea.pagamentos_api.models.Permissao;
import uea.pagamentos_api.repositories.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao criar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	public List<Permissao> listar(){
		return permissaoRepository.findAll();
	}
	
	public Permissao buscarPorCodigo(Long codigo) {
		Permissao permissao = permissaoRepository.findById(codigo).orElseThrow();
		return permissao;
	}
	
	public void excluir(Long codigo) {
		permissaoRepository.deleteById(codigo);
	}
	
	public Permissao atualizar(Long codigo, Permissao permissao) {
		Permissao permissaoSalva = permissaoRepository.
				findById(codigo).orElseThrow();
		BeanUtils.copyProperties(permissao, permissaoSalva, "codigo");
		return permissaoRepository.save(permissaoSalva);
	}
	

}
