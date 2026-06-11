package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.auth.LoginRequest;
import co.edu.uniremington.portal.dto.auth.LoginResponse;
import co.edu.uniremington.portal.dto.auth.RegisterRequest;
import co.edu.uniremington.portal.entity.Role;
import co.edu.uniremington.portal.entity.Usuario;
import co.edu.uniremington.portal.repository.UsuarioRepository;
import co.edu.uniremington.portal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest req) {
        Usuario user = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inactivo");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationMs())
                .user(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .role(user.getRole().name())
                        .build())
                .build();
    }

    public LoginResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        Role role = Role.STUDENT;

        Usuario user = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .email(req.getEmail())
                .fullName(req.getFullName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .isActive(true)
                .build();

        usuarioRepository.save(user);

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationMs())
                .user(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .role(user.getRole().name())
                        .build())
                .build();
    }

    public void forgotPassword(String email) {
        // Aquí enviarías un email con un token. Por simplicidad, solo log.
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            u.setResetToken(UUID.randomUUID().toString());
            usuarioRepository.save(u);
        });
    }
}
