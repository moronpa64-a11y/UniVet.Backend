package co.edu.uniremington.portal.dto.noticia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Noticia - usa nombres en español para el frontend.
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NoticiaDTO {
    private Long id;
    private String titulo;          // → title en BD
    private String contenido;       // → content en BD
    private String categoria;       // → category en BD
    private String imagenUrl;       // → image_url en BD
    private String autor;           // → author en BD
    private String fechaPublicacion;
}
