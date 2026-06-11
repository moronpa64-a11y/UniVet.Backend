package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.historiaclinica.HistoriaClinicaDTO;
import co.edu.uniremington.portal.entity.HistoriaClinica;
import co.edu.uniremington.portal.repository.HistoriaClinicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository repository;

    public List<HistoriaClinicaDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public HistoriaClinicaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historia clínica no encontrada"));
    }

    public HistoriaClinicaDTO crear(HistoriaClinicaDTO dto) {
        HistoriaClinica h = toEntity(dto);
        h.setId(null);
        return toDTO(repository.save(h));
    }

    public HistoriaClinicaDTO actualizar(Long id, HistoriaClinicaDTO dto) {
        HistoriaClinica h = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historia clínica no encontrada"));
        if (dto.getUsuario() != null) h.setUsuario(dto.getUsuario());
        if (dto.getMascota() != null) h.setMascota(dto.getMascota());
        if (dto.getRaza() != null) h.setRaza(dto.getRaza());
        if (dto.getEspecie() != null) h.setEspecie(dto.getEspecie());
        if (dto.getFecha() != null) h.setFecha(dto.getFecha());
        if (dto.getPadecimiento() != null) h.setPadecimiento(dto.getPadecimiento());
        if (dto.getMedicamento() != null) h.setMedicamento(dto.getMedicamento());
        if (dto.getDosis() != null) h.setDosis(dto.getDosis());
        if (dto.getMiligramos() != null) h.setMiligramos(dto.getMiligramos());
        if (dto.getEnRevision() != null) h.setEnRevision(dto.getEnRevision());
        if (dto.getProximoControl() != null) h.setProximoControl(dto.getProximoControl());
        if (dto.getDadoDeAlta() != null) h.setDadoDeAlta(dto.getDadoDeAlta());
        if (dto.getObservaciones() != null) h.setObservaciones(dto.getObservaciones());
        return toDTO(repository.save(h));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Historia clínica no encontrada");
        repository.deleteById(id);
    }

    private HistoriaClinicaDTO toDTO(HistoriaClinica h) {
        HistoriaClinicaDTO dto = new HistoriaClinicaDTO();
        dto.setId(h.getId());
        dto.setUsuario(h.getUsuario());
        dto.setMascota(h.getMascota());
        dto.setRaza(h.getRaza());
        dto.setEspecie(h.getEspecie());
        dto.setFecha(h.getFecha());
        dto.setPadecimiento(h.getPadecimiento());
        dto.setMedicamento(h.getMedicamento());
        dto.setDosis(h.getDosis());
        dto.setMiligramos(h.getMiligramos());
        dto.setEnRevision(h.getEnRevision());
        dto.setProximoControl(h.getProximoControl());
        dto.setDadoDeAlta(h.getDadoDeAlta());
        dto.setObservaciones(h.getObservaciones());
        return dto;
    }

    private HistoriaClinica toEntity(HistoriaClinicaDTO dto) {
        return HistoriaClinica.builder()
                .usuario(dto.getUsuario())
                .mascota(dto.getMascota())
                .raza(dto.getRaza())
                .especie(dto.getEspecie())
                .fecha(dto.getFecha())
                .padecimiento(dto.getPadecimiento())
                .medicamento(dto.getMedicamento())
                .dosis(dto.getDosis())
                .miligramos(dto.getMiligramos())
                .enRevision(dto.getEnRevision() != null ? dto.getEnRevision() : false)
                .proximoControl(dto.getProximoControl())
                .dadoDeAlta(dto.getDadoDeAlta() != null ? dto.getDadoDeAlta() : false)
                .observaciones(dto.getObservaciones())
                .build();
    }
}
