package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.foro.ForoDTO;
import co.edu.uniremington.portal.dto.foro.ForoRespuestaDTO;
import co.edu.uniremington.portal.service.ForoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foros")
@RequiredArgsConstructor
public class ForoController {

    private final ForoService service;

    @GetMapping public List<ForoDTO> listar() { return service.listar(); }
    @GetMapping("/{id}") public ForoDTO obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping public ForoDTO crear(@RequestBody ForoDTO dto) { return service.crear(dto); }

    @PostMapping("/{id}/respuestas")
    public ForoDTO agregarRespuesta(@PathVariable Long id, @RequestBody ForoRespuestaDTO dto) {
        return service.agregarRespuesta(id, dto);
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
