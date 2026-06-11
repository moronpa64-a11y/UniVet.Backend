package co.edu.uniremington.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que mapea la tabla `usuarios`.
 */
@Entity
@Table(name = "usuarios")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "dni", unique = true)
    private String dni;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "professional_card")
    private String professionalCard;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expires_at")
    private LocalDateTime resetTokenExpiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
    }
}
