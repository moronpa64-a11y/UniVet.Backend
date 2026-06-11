package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.usuario.UsuarioDTO;
import co.edu.uniremington.portal.entity.Role;
import co.edu.uniremington.portal.entity.Usuario;
import co.edu.uniremington.portal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }

    public UsuarioDTO obtener(String id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return toDTO(u);
    }

    public UsuarioDTO crear(UsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe");
        }

        Usuario u = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "Password123*"))
                .role(parseRole(dto.getRole()))
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .dni(dto.getDni())
                .faculty(dto.getFaculty())
                .professionalCard(dto.getProfessionalCard())
                .studentCode(dto.getStudentCode())
                .semester(dto.getSemester())
                .telefono(dto.getTelefono())
                .build();

        return toDTO(usuarioRepository.save(u));
    }

    public UsuarioDTO actualizar(String id, UsuarioDTO dto) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (dto.getFullName() != null) u.setFullName(dto.getFullName());
        if (dto.getRole() != null) u.setRole(parseRole(dto.getRole()));
        if (dto.getDni() != null) u.setDni(dto.getDni());
        if (dto.getFaculty() != null) u.setFaculty(dto.getFaculty());
        if (dto.getProfessionalCard() != null) u.setProfessionalCard(dto.getProfessionalCard());
        if (dto.getStudentCode() != null) u.setStudentCode(dto.getStudentCode());
        if (dto.getSemester() != null) u.setSemester(dto.getSemester());
        if (dto.getTelefono() != null) u.setTelefono(dto.getTelefono());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return toDTO(usuarioRepository.save(u));
    }

    public UsuarioDTO cambiarEstado(String id, String estado) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        u.setIsActive("ACTIVO".equalsIgnoreCase(estado));
        return toDTO(usuarioRepository.save(u));
    }

    public void eliminar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private Role parseRole(String role) {
        if (role == null) return Role.STUDENT;
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            return Role.STUDENT;
        }
    }

    public UsuarioDTO toDTO(Usuario u) {
        return UsuarioDTO.builder()
                .id(u.getId())
                .fullName(u.getFullName())
                .email(u.getEmail())
                .role(u.getRole() != null ? u.getRole().name() : null)
                .isActive(u.getIsActive())
                .dni(u.getDni())
                .faculty(u.getFaculty())
                .professionalCard(u.getProfessionalCard())
                .studentCode(u.getStudentCode())
                .semester(u.getSemester())
                .telefono(u.getTelefono())
                .build();
    }
}
