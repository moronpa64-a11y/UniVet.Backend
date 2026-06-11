package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.evento.EventoDTO;
import co.edu.uniremington.portal.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @GetMapping public List<EventoDTO> listar() { return service.listar(); }
    @GetMapping("/{id}") public EventoDTO obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping public EventoDTO crear(@RequestBody EventoDTO dto) { return service.crear(dto); }
    @PutMapping("/{id}") public EventoDTO actualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        return service.actualizar(id, dto);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
