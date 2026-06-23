package com.example.NotificationServiceQuickBite.Controlador;


import com.example.NotificationServiceQuickBite.dto.NotificacionDto;
import com.example.NotificationServiceQuickBite.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacion(@RequestBody NotificacionDto dto) {
        service.enviarEmail(dto);
        return ResponseEntity.ok("Proceso de notificación ejecutado con éxito.");
    }
}