package uea.pagamentos_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import uea.pagamentos_api.models.Usuario;
import uea.pagamentos_api.repositories.UsuarioRepository;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
		return new UserDetailsImpl(usuario.getEmail(),usuario.getSenha(),usuario.getPermissoes());
	}
}
