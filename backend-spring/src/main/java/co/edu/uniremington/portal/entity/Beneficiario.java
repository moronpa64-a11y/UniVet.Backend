package co.edu.uniremington.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "beneficiarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String documento;

    private String email;

    private String telefono;

    private String direccion;

    @Builder.Default
    private Boolean estado = true;
}
