package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.historiaclinica.HistoriaClinicaDTO;
import co.edu.uniremington.portal.service.HistoriaClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historias-clinicas")
@RequiredArgsConstructor
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIAN')")
    public List<HistoriaClinicaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIAN')")
    public HistoriaClinicaDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIAN')")
    public ResponseEntity<HistoriaClinicaDTO> crear(@RequestBody HistoriaClinicaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIAN')")
    public HistoriaClinicaDTO actualizar(@PathVariable Long id, @RequestBody HistoriaClinicaDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIAN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
