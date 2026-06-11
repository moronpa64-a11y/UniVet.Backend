package co.edu.uniremington.portal.dto.foro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ForoDTO {
    private Long id;
    private String titulo;          // → title
    private String contenido;       // → description
    private String autor;           // → author_name
    private String email;           // → author_email
    private String fecha;           // → created_at
    private Integer repliesCount;
    private List<ForoRespuestaDTO> respuestas;
}
