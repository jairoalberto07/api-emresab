package api_emresab.repository;

import api_emresab.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método para buscar un usuario por su nombre de usuario único
    Optional<Usuario> findByUsername(String username);
}