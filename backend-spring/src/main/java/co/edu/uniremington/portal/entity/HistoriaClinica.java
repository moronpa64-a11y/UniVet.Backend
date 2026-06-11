package co.edu.uniremington.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "historias_clinicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private String mascota;

    private String raza;

    private String especie;

    private String fecha;

    @Column(columnDefinition = "TEXT")
    private String padecimiento;

    private String medicamento;

    private String dosis;

    private Integer miligramos;

    @Builder.Default
    private Boolean enRevision = false;

    private String proximoControl;

    @Builder.Default
    private Boolean dadoDeAlta = false;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
