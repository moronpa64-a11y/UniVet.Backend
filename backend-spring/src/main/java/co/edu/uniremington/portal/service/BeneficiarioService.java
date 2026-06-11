package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.beneficiario.BeneficiarioDTO;
import co.edu.uniremington.portal.entity.Beneficiario;
import co.edu.uniremington.portal.repository.BeneficiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiarioService {

    private final BeneficiarioRepository repository;

    public List<BeneficiarioDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public BeneficiarioDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiario no encontrado"));
    }

    public BeneficiarioDTO crear(BeneficiarioDTO dto) {
        Beneficiario b = Beneficiario.builder()
                .nombre(dto.getNombre())
                .documento(dto.getDocumento())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();
        return toDTO(repository.save(b));
    }

    public BeneficiarioDTO actualizar(Long id, BeneficiarioDTO dto) {
        Beneficiario b = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiario no encontrado"));
        if (dto.getNombre() != null) b.setNombre(dto.getNombre());
        if (dto.getDocumento() != null) b.setDocumento(dto.getDocumento());
        if (dto.getEmail() != null) b.setEmail(dto.getEmail());
        if (dto.getTelefono() != null) b.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null) b.setDireccion(dto.getDireccion());
        if (dto.getEstado() != null) b.setEstado(dto.getEstado());
        return toDTO(repository.save(b));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiario no encontrado");
        repository.deleteById(id);
    }

    private BeneficiarioDTO toDTO(Beneficiario b) {
        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setId(b.getId());
        dto.setNombre(b.getNombre());
        dto.setDocumento(b.getDocumento());
        dto.setEmail(b.getEmail());
        dto.setTelefono(b.getTelefono());
        dto.setDireccion(b.getDireccion());
        dto.setEstado(b.getEstado());
        return dto;
    }
}
