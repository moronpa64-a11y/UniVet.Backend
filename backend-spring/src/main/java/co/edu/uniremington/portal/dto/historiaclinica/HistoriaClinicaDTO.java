package co.edu.uniremington.portal.dto.historiaclinica;

import lombok.Data;

@Data
public class HistoriaClinicaDTO {
    private Long id;
    private String usuario;
    private String mascota;
    private String raza;
    private String especie;
    private String fecha;
    private String padecimiento;
    private String medicamento;
    private String dosis;
    private Integer miligramos;
    private Boolean enRevision;
    private String proximoControl;
    private Boolean dadoDeAlta;
    private String observaciones;
}
