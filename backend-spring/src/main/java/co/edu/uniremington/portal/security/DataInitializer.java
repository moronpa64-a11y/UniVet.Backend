package co.edu.uniremington.portal.security;

import co.edu.uniremington.portal.entity.Role;
import co.edu.uniremington.portal.entity.Usuario;
import co.edu.uniremington.portal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Unificado en DataSeeder.java (config/DataSeeder.java). Esta clase queda desactivada.
// @Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@correo.com";

        usuarioRepository.findByEmail(adminEmail).ifPresentOrElse(
                existing -> {
                    // Si el usuario ya existe, no hacemos nada.
                },
                () -> {
                    Usuario admin = Usuario.builder()
                            .id(java.util.UUID.randomUUID().toString())
                            .email(adminEmail)
                            .fullName("Administrador del Sistema")
                            .password(passwordEncoder.encode("admin"))
                            .role(Role.ADMIN)
                            .isActive(true)
                            .dni("0000000000")
                            .faculty("Administración")
                            .build();
                    usuarioRepository.save(admin);
                });
    }
}
