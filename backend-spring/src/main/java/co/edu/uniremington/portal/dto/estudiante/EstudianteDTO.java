package co.edu.uniremington.portal.dto.estudiante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EstudianteDTO {
    private String id;
    private String nombre;             // → full_name
    private String email;
    private String telefono;
    private String documento;          // → dni
    private String programa;           // → faculty
    private String codigoEstudiante;   // → student_code
    private Integer semestre;          // → semester
    private String password;           // Solo entrada
    private String estado;             // "ACTIVO"/"INACTIVO" → is_active
    private String fechaIngreso;
}
