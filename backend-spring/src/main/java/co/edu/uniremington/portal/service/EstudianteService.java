package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.estudiante.EstudianteDTO;
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
public class EstudianteService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<EstudianteDTO> listar() {
        return usuarioRepository.findByRole(Role.STUDENT).stream().map(this::toDTO).toList();
    }

    public EstudianteDTO obtener(String id) {
        return toDTO(findStudent(id));
    }

    public EstudianteDTO crear(EstudianteDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe");
        }
        Usuario u = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .fullName(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "Estudiante123*"))
                .role(Role.STUDENT)
                .isActive(true)
                .dni(dto.getDocumento())
                .faculty(dto.getPrograma())
                .studentCode(dto.getCodigoEstudiante())
                .semester(dto.getSemestre())
                .telefono(dto.getTelefono())
                .build();
        return toDTO(usuarioRepository.save(u));
    }

    public EstudianteDTO actualizar(String id, EstudianteDTO dto) {
        Usuario u = findStudent(id);
        if (dto.getNombre() != null) u.setFullName(dto.getNombre());
        if (dto.getDocumento() != null) u.setDni(dto.getDocumento());
        if (dto.getPrograma() != null) u.setFaculty(dto.getPrograma());
        if (dto.getCodigoEstudiante() != null) u.setStudentCode(dto.getCodigoEstudiante());
        if (dto.getSemestre() != null) u.setSemester(dto.getSemestre());
        if (dto.getTelefono() != null) u.setTelefono(dto.getTelefono());
        if (dto.getEstado() != null) u.setIsActive("ACTIVO".equalsIgnoreCase(dto.getEstado()));
        return toDTO(usuarioRepository.save(u));
    }

    public void eliminar(String id) {
        usuarioRepository.delete(findStudent(id));
    }

    private Usuario findStudent(String id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
        if (u.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado");
        }
        return u;
    }

    private EstudianteDTO toDTO(Usuario u) {
        return EstudianteDTO.builder()
                .id(u.getId())
                .nombre(u.getFullName())
                .email(u.getEmail())
                .documento(u.getDni())
                .programa(u.getFaculty())
                .codigoEstudiante(u.getStudentCode())
                .semestre(u.getSemester())
                .telefono(u.getTelefono())
                .estado(Boolean.TRUE.equals(u.getIsActive()) ? "ACTIVO" : "INACTIVO")
                .fechaIngreso(u.getCreatedAt() != null ? u.getCreatedAt().toString() : null)
                .build();
    }
}
