package co.edu.uniremington.portal.repository;

import co.edu.uniremington.portal.entity.Role;
import co.edu.uniremington.portal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByResetToken(String resetToken);
    List<Usuario> findByRole(Role role);
    boolean existsByEmail(String email);
}
