package com.example.NotificationServiceQuickBite.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String email = request.getHeader("X-User-Email");
        String role = request.getHeader("X-User-Role");
        String uri = request.getRequestURI();

        if (email != null && role != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // LOG DE TRAZABILIDAD: Te dice qué endpoint de notificaciones se está ejecutando
            log.info("NOTIFICACIONES INTERCEPTADAS: [{}] {} | Usuario: {} | Rol: {}", request.getMethod(), uri, email, role);
        } else {
            log.warn("ALERTA: Intento de acceso directo a Notificaciones sin pasar por el Gateway desde: {}", request.getRemoteAddr());
        }

        filterChain.doFilter(request, response);
    }
}