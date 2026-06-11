package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.noticia.NoticiaDTO;
import co.edu.uniremington.portal.entity.News;
import co.edu.uniremington.portal.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticiaService {

    private final NewsRepository repo;

    public List<NoticiaDTO> listar() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    public NoticiaDTO obtener(Long id) {
        return toDTO(find(id));
    }

    public NoticiaDTO crear(NoticiaDTO dto) {
        News n = News.builder()
                .title(dto.getTitulo())
                .content(dto.getContenido())
                .category(dto.getCategoria())
                .imageUrl(dto.getImagenUrl())
                .author(dto.getAutor())
                .build();
        return toDTO(repo.save(n));
    }

    public NoticiaDTO actualizar(Long id, NoticiaDTO dto) {
        News n = find(id);
        if (dto.getTitulo() != null) n.setTitle(dto.getTitulo());
        if (dto.getContenido() != null) n.setContent(dto.getContenido());
        if (dto.getCategoria() != null) n.setCategory(dto.getCategoria());
        if (dto.getImagenUrl() != null) n.setImageUrl(dto.getImagenUrl());
        if (dto.getAutor() != null) n.setAuthor(dto.getAutor());
        return toDTO(repo.save(n));
    }

    public void eliminar(Long id) {
        News n = find(id);
        repo.delete(n);
    }

    private News find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Noticia no encontrada"));
    }

    private NoticiaDTO toDTO(News n) {
        return NoticiaDTO.builder()
                .id(n.getId())
                .titulo(n.getTitle())
                .contenido(n.getContent())
                .categoria(n.getCategory())
                .imagenUrl(n.getImageUrl())
                .autor(n.getAuthor())
                .fechaPublicacion(n.getPublishDate() != null ? n.getPublishDate().toString() : null)
                .build();
    }
}
