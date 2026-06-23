package com.example.NotificationServiceQuickBite.service;


import com.example.NotificationServiceQuickBite.Modelo.Notificacion;
import com.example.NotificationServiceQuickBite.dto.NotificacionDto;

import com.example.NotificationServiceQuickBite.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(NotificacionDto dto) {
        Notificacion notificacion = Notificacion.builder()
                .usuarioId(dto.getUsuarioId())
                .destinatarioEmail(dto.getDestinatarioEmail())
                .asunto(dto.getAsunto())
                .mensaje(dto.getMensaje())
                .fechaEnvio(LocalDateTime.now())
                .build();

        try {
            // Configurar y mandar el mail real
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.getDestinatarioEmail());
            message.setSubject(dto.getAsunto());
            message.setText(dto.getMensaje());

            mailSender.send(message);

            notificacion.setEstado("ENVIADO");
        } catch (Exception e) {
            notificacion.setEstado("FALLIDO");
            System.err.println("Error al enviar email: " + e.getMessage());
        }

        // Persistir en base de datos local
        repository.save(notificacion);
    }
}