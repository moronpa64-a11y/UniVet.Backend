package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.estudiante.EstudianteDTO;
import co.edu.uniremington.portal.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService service;

    @GetMapping public List<EstudianteDTO> listar() { return service.listar(); }
    @GetMapping("/{id}") public EstudianteDTO obtener(@PathVariable String id) { return service.obtener(id); }
    @PostMapping public EstudianteDTO crear(@RequestBody EstudianteDTO dto) { return service.crear(dto); }
    @PutMapping("/{id}") public EstudianteDTO actualizar(@PathVariable String id, @RequestBody EstudianteDTO dto) {
        return service.actualizar(id, dto);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
