package uea.pagamentos_api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uea.pagamentos_api.models.Usuario;
import uea.pagamentos_api.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario criar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarPorCodigo(Long codigo) {
		Usuario usuario = usuarioRepository.findById(codigo).orElseThrow();
		return usuario;
	}
	
	public void excluir(Long codigo) {
		usuarioRepository.deleteById(codigo);
	}
	
	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalva = usuarioRepository.
				findById(codigo).orElseThrow();
		BeanUtils.copyProperties(usuario, usuarioSalva, "codigo");
		return usuarioRepository.save(usuarioSalva);
	}
	

}
