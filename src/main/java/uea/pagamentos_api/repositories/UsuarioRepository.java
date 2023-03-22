package uea.pagamentos_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uea.pagamentos_api.models.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByEmail(String email);
}
