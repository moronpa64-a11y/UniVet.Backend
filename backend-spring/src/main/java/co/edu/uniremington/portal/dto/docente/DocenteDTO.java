package co.edu.uniremington.portal.dto.docente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DocenteDTO {
    private String id;
    private String nombre;            // → full_name
    private String email;
    private String telefono;
    private String especialidad;      // → faculty
    private String documento;         // → dni
    private String tarjetaProfesional; // → professional_card
    private String password;          // Solo entrada
    private String estado;            // "ACTIVO" / "INACTIVO" → is_active
    private String fechaVinculacion;
}
