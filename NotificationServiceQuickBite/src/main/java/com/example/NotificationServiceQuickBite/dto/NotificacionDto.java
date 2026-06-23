package com.example.NotificationServiceQuickBite.dto;


import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {
    private UUID usuarioId;
    private String destinatarioEmail;
    private String asunto;
    private String mensaje;
}