package com.example.NotificationServiceQuickBite.Modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID usuarioId;
    private String destinatarioEmail;
    private String asunto;

    @Column(length = 1000)
    private String mensaje;

    private LocalDateTime fechaEnvio;
    private String estado; // ENVIADO, FALLIDO
}