package co.edu.uniremington.portal.dto.foro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ForoRespuestaDTO {
    private Long id;
    private String autor;       // → author_name
    private String contenido;   // → content
    private String fecha;       // → created_at
}
