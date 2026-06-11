package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.usuario.CambiarEstadoRequest;
import co.edu.uniremington.portal.dto.usuario.UsuarioDTO;
import co.edu.uniremington.portal.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtener(@PathVariable String id) {
        return service.obtener(id);
    }

    @PostMapping
    public UsuarioDTO crear(@RequestBody UsuarioDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable String id, @RequestBody UsuarioDTO dto) {
        return service.actualizar(id, dto);
    }

    @PatchMapping("/{id}/estado")
    public UsuarioDTO cambiarEstado(@PathVariable String id, @RequestBody CambiarEstadoRequest req) {
        return service.cambiarEstado(id, req.getEstado());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
