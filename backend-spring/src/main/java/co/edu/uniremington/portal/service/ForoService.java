package co.edu.uniremington.portal.service;

import co.edu.uniremington.portal.dto.foro.ForoDTO;
import co.edu.uniremington.portal.dto.foro.ForoRespuestaDTO;
import co.edu.uniremington.portal.entity.ForumReply;
import co.edu.uniremington.portal.entity.ForumTopic;
import co.edu.uniremington.portal.repository.ForumReplyRepository;
import co.edu.uniremington.portal.repository.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForoService {

    private final ForumTopicRepository topicRepo;
    private final ForumReplyRepository replyRepo;

    @Transactional(readOnly = true)
    public List<ForoDTO> listar() {
        return topicRepo.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ForoDTO obtener(Long id) {
        return toDTO(find(id));
    }

    @Transactional
    public ForoDTO crear(ForoDTO dto) {
        ForumTopic t = ForumTopic.builder()
                .title(dto.getTitulo())
                .description(dto.getContenido())
                .authorName(dto.getAutor())
                .authorEmail(dto.getEmail())
                .repliesCount(0)
                .build();
        return toDTO(topicRepo.save(t));
    }

    @Transactional
    public ForoDTO agregarRespuesta(Long topicId, ForoRespuestaDTO dto) {
        ForumTopic t = find(topicId);
        ForumReply reply = ForumReply.builder()
                .topic(t)
                .authorName(dto.getAutor())
                .content(dto.getContenido())
                .build();
        replyRepo.save(reply);
        t.setRepliesCount(t.getRepliesCount() + 1);
        topicRepo.save(t);
        return toDTO(t);
    }

    @Transactional
    public void eliminar(Long id) {
        topicRepo.delete(find(id));
    }

    private ForumTopic find(Long id) {
        return topicRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foro no encontrado"));
    }

    private ForoDTO toDTO(ForumTopic t) {
        List<ForoRespuestaDTO> respuestas = (t.getReplies() != null ? t.getReplies() : List.<ForumReply>of())
                .stream()
                .map(r -> ForoRespuestaDTO.builder()
                        .id(r.getId())
                        .autor(r.getAuthorName())
                        .contenido(r.getContent())
                        .fecha(r.getCreatedAt() != null ? r.getCreatedAt().toString() : null)
                        .build())
                .toList();

        return ForoDTO.builder()
                .id(t.getId())
                .titulo(t.getTitle())
                .contenido(t.getDescription())
                .autor(t.getAuthorName())
                .email(t.getAuthorEmail())
                .fecha(t.getCreatedAt() != null ? t.getCreatedAt().toString() : null)
                .repliesCount(t.getRepliesCount())
                .respuestas(respuestas)
                .build();
    }
}
