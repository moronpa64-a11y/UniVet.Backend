package co.edu.uniremington.portal.dto.usuario;

import lombok.Data;

@Data
public class CambiarEstadoRequest {
    private String estado;  // "ACTIVO" o "INACTIVO"
}
