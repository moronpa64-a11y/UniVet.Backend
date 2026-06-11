package co.edu.uniremington.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "veterinary_journeys")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class VeterinaryJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "journey_date", nullable = false)
    private LocalDateTime journeyDate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "status")
    private String status;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "organizador")
    private String organizador;

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;
}
