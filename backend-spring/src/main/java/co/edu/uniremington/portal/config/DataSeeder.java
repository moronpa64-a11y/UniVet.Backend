package co.edu.uniremington.portal.config;

import co.edu.uniremington.portal.entity.Role;
import co.edu.uniremington.portal.entity.Usuario;
import co.edu.uniremington.portal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializador: crea usuarios por defecto si no existen.
 *
 * Esto garantiza que las contraseñas BCrypt sean válidas (a veces los hashes copiados
 * de internet fallan; aquí Spring las genera frescas).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seed("admin@uniremington.edu.co",   "Admin2026*",      "Administrador Principal", Role.ADMIN,        "1000000001", "General");
        seed("docente@uniremington.edu.co", "Docente2026*",    "Profesor Carlos Jaramillo", Role.TEACHER,    "1000000002", "Medicina Veterinaria");
        seed("estudiante@uniremington.edu.co","Estudiante2026*","María Estudiante García", Role.STUDENT,    "1000000003", "Medicina Veterinaria");
        seed("veterinario@uniremington.edu.co","Vet2026*",     "Dr. Andrés Veterinario",  Role.VETERINARIAN,"1000000004", "Medicina Veterinaria");

        log.info("✅ DataSeeder: Usuarios verificados/creados");
    }

    private void seed(String email, String password, String fullName, Role role, String dni, String faculty) {
        usuarioRepository.findByEmail(email).ifPresentOrElse(
            existing -> {
                // Actualizar password si está usando hash inválido
                if (existing.getPassword() == null || !existing.getPassword().startsWith("$2")) {
                    existing.setPassword(passwordEncoder.encode(password));
                    usuarioRepository.save(existing);
                    log.info("🔧 Password actualizada para: {}", email);
                }
            },
            () -> {
                Usuario u = Usuario.builder()
                        .id(java.util.UUID.randomUUID().toString())
                        .email(email)
                        .fullName(fullName)
                        .password(passwordEncoder.encode(password))
                        .role(role)
                        .isActive(true)
                        .dni(dni)
                        .faculty(faculty)
                        .build();
                usuarioRepository.save(u);
                log.info("➕ Usuario creado: {} ({})", email, role);
            }
        );
    }
}
