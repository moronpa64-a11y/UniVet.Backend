package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.noticia.NoticiaDTO;
import co.edu.uniremington.portal.service.NoticiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
@RequiredArgsConstructor
public class NoticiaController {

    private final NoticiaService service;

    @GetMapping public List<NoticiaDTO> listar() { return service.listar(); }
    @GetMapping("/{id}") public NoticiaDTO obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping public NoticiaDTO crear(@RequestBody NoticiaDTO dto) { return service.crear(dto); }
    @PutMapping("/{id}") public NoticiaDTO actualizar(@PathVariable Long id, @RequestBody NoticiaDTO dto) {
        return service.actualizar(id, dto);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
