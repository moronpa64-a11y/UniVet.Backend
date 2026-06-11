package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.docente.DocenteDTO;
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
public class DocenteService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<DocenteDTO> listar() {
        return usuarioRepository.findByRole(Role.TEACHER).stream().map(this::toDTO).toList();
    }

    public DocenteDTO obtener(String id) {
        Usuario u = findTeacher(id);
        return toDTO(u);
    }

    public DocenteDTO crear(DocenteDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe");
        }
        Usuario u = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .fullName(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "Docente123*"))
                .role(Role.TEACHER)
                .isActive(true)
                .dni(dto.getDocumento())
                .faculty(dto.getEspecialidad())
                .professionalCard(dto.getTarjetaProfesional())
                .telefono(dto.getTelefono())
                .build();
        return toDTO(usuarioRepository.save(u));
    }

    public DocenteDTO actualizar(String id, DocenteDTO dto) {
        Usuario u = findTeacher(id);
        if (dto.getNombre() != null) u.setFullName(dto.getNombre());
        if (dto.getDocumento() != null) u.setDni(dto.getDocumento());
        if (dto.getEspecialidad() != null) u.setFaculty(dto.getEspecialidad());
        if (dto.getTarjetaProfesional() != null) u.setProfessionalCard(dto.getTarjetaProfesional());
        if (dto.getTelefono() != null) u.setTelefono(dto.getTelefono());
        if (dto.getEstado() != null) u.setIsActive("ACTIVO".equalsIgnoreCase(dto.getEstado()));
        return toDTO(usuarioRepository.save(u));
    }

    public void eliminar(String id) {
        Usuario u = findTeacher(id);
        usuarioRepository.delete(u);
    }

    private Usuario findTeacher(String id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado"));
        if (u.getRole() != Role.TEACHER) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado");
        }
        return u;
    }

    private DocenteDTO toDTO(Usuario u) {
        return DocenteDTO.builder()
                .id(u.getId())
                .nombre(u.getFullName())
                .email(u.getEmail())
                .documento(u.getDni())
                .especialidad(u.getFaculty())
                .tarjetaProfesional(u.getProfessionalCard())
                .telefono(u.getTelefono())
                .estado(Boolean.TRUE.equals(u.getIsActive()) ? "ACTIVO" : "INACTIVO")
                .fechaVinculacion(u.getCreatedAt() != null ? u.getCreatedAt().toString() : null)
                .build();
    }
}
