package co.edu.uniremington.portal.dto.evento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventoDTO {
    private Long id;
    private String titulo;        // → name
    private String descripcion;   // → descripcion
    private String fecha;         // → journey_date
    private String lugar;         // → location
    private String organizador;
    private String tipo;          // → service_type
    private String estado;        // → status
    private Integer maxCapacidad; // → max_capacity
    private String imagenUrl;     // → imagen_url
}
