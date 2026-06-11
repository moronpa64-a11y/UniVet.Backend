package co.edu.uniremington.portal.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private String id;
    private String fullName;
    private String email;
    private String password;       // Solo entrada (no se devuelve)
    private String role;
    private Boolean isActive;
    private String dni;
    private String faculty;
    private String professionalCard;
    private String studentCode;
    private Integer semester;
    private String telefono;
}
