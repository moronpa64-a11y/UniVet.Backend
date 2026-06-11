package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.docente.DocenteDTO;
import co.edu.uniremington.portal.service.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService service;

    @GetMapping
    public List<DocenteDTO> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public DocenteDTO obtener(@PathVariable String id) { return service.obtener(id); }

    @PostMapping
    public DocenteDTO crear(@RequestBody DocenteDTO dto) { return service.crear(dto); }

    @PutMapping("/{id}")
    public DocenteDTO actualizar(@PathVariable String id, @RequestBody DocenteDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
