package co.edu.uniremington.portal.dto.beneficiario;

import lombok.Data;

@Data
public class BeneficiarioDTO {
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private String direccion;
    private Boolean estado;
}
