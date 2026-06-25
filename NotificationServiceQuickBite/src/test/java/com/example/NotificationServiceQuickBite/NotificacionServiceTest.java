package com.example.NotificationServiceQuickBite;
import com.example.NotificationServiceQuickBite.Modelo.Notificacion;
import com.example.NotificationServiceQuickBite.dto.NotificacionDto;
import com.example.NotificationServiceQuickBite.repository.NotificacionRepository;
import com.example.NotificationServiceQuickBite.service.NotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificacionService notificacionService;

    private NotificacionDto dtoPrueba;

    @BeforeEach
    void setUp() {
        dtoPrueba = new NotificacionDto();

        dtoPrueba.setUsuarioId(UUID.randomUUID());
        dtoPrueba.setDestinatarioEmail("test@quickbite.com");
        dtoPrueba.setAsunto("Confirmación de Pedido");
        dtoPrueba.setMensaje("Tu pedido de QuickBite está en camino.");
    }

    @Test
    void cuandoElEnvioEsExitoso_debeGuardarNotificacionComoENVIADO() {
        ArgumentCaptor<Notificacion> notificacionCaptor = ArgumentCaptor.forClass(Notificacion.class);

        notificacionService.enviarEmail(dtoPrueba);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(repository, times(1)).save(notificacionCaptor.capture());

        Notificacion notificacionGuardada = notificacionCaptor.getValue();

        assertEquals("ENVIADO", notificacionGuardada.getEstado());
        assertEquals(dtoPrueba.getUsuarioId(), notificacionGuardada.getUsuarioId());
        assertNotNull(notificacionGuardada.getFechaEnvio());
    }

    @Test
    void cuandoElEnvioFalla_debeGuardarNotificacionComoFALLIDO() {
        doThrow(new RuntimeException("Error simulado de red"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        ArgumentCaptor<Notificacion> notificacionCaptor = ArgumentCaptor.forClass(Notificacion.class);

        notificacionService.enviarEmail(dtoPrueba);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(repository, times(1)).save(notificacionCaptor.capture());

        Notificacion notificacionGuardada = notificacionCaptor.getValue();

        assertEquals("FALLIDO", notificacionGuardada.getEstado());
    }
}