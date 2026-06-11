package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.evento.EventoDTO;
import co.edu.uniremington.portal.entity.VeterinaryJourney;
import co.edu.uniremington.portal.repository.VeterinaryJourneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final VeterinaryJourneyRepository repo;

    public List<EventoDTO> listar() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    public EventoDTO obtener(Long id) {
        return toDTO(find(id));
    }

    public EventoDTO crear(EventoDTO dto) {
        VeterinaryJourney e = VeterinaryJourney.builder()
                .name(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .journeyDate(parseDate(dto.getFecha()))
                .location(dto.getLugar() != null ? dto.getLugar() : "Por definir")
                .maxCapacity(dto.getMaxCapacidad() != null ? dto.getMaxCapacidad() : 50)
                .serviceType(dto.getTipo() != null ? dto.getTipo() : "BRIGADA")
                .status(dto.getEstado() != null ? dto.getEstado() : "PROGRAMADO")
                .organizador(dto.getOrganizador())
                .imagenUrl(dto.getImagenUrl())
                .build();
        return toDTO(repo.save(e));
    }

    public EventoDTO actualizar(Long id, EventoDTO dto) {
        VeterinaryJourney e = find(id);
        if (dto.getTitulo() != null) e.setName(dto.getTitulo());
        if (dto.getDescripcion() != null) e.setDescripcion(dto.getDescripcion());
        if (dto.getFecha() != null) e.setJourneyDate(parseDate(dto.getFecha()));
        if (dto.getLugar() != null) e.setLocation(dto.getLugar());
        if (dto.getMaxCapacidad() != null) e.setMaxCapacity(dto.getMaxCapacidad());
        if (dto.getTipo() != null) e.setServiceType(dto.getTipo());
        if (dto.getEstado() != null) e.setStatus(dto.getEstado());
        if (dto.getOrganizador() != null) e.setOrganizador(dto.getOrganizador());
        if (dto.getImagenUrl() != null) e.setImagenUrl(dto.getImagenUrl());
        return toDTO(repo.save(e));
    }

    public void eliminar(Long id) {
        repo.delete(find(id));
    }

    private VeterinaryJourney find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
    }

    private LocalDateTime parseDate(String fecha) {
        if (fecha == null || fecha.isBlank()) return LocalDateTime.now();
        try {
            if (fecha.length() == 10) return LocalDateTime.parse(fecha + "T00:00:00");
            return LocalDateTime.parse(fecha);
        } catch (DateTimeParseException e) {
            return LocalDateTime.now();
        }
    }

    private EventoDTO toDTO(VeterinaryJourney e) {
        return EventoDTO.builder()
                .id(e.getId())
                .titulo(e.getName())
                .descripcion(e.getDescripcion())
                .fecha(e.getJourneyDate() != null ? e.getJourneyDate().toString() : null)
                .lugar(e.getLocation())
                .maxCapacidad(e.getMaxCapacity())
                .tipo(e.getServiceType())
                .estado(e.getStatus())
                .organizador(e.getOrganizador())
                .imagenUrl(e.getImagenUrl())
                .build();
    }
}
