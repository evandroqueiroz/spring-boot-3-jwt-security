package com.hubunity.core.security.config;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.security.user.Role;
import com.hubunity.core.security.user.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que extrai e armazena o ID da empresa no TenantContext
 * baseado no usuário autenticado e sua role.
 */
@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    // ID fixo da empresa master para usuários ADMIN
    private static final String MASTER_COMPANY_ID = "f008f749-28a6-41ad-953a-e9c2d14a7fdacc";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()
                    && authentication.getPrincipal() instanceof User) {

                User user = (User) authentication.getPrincipal();
                String idEmpresa;

                // Se o usuário for ADMIN, usa o ID da empresa master
                if (user.getRole() == Role.ADMIN) {
                    idEmpresa = MASTER_COMPANY_ID;
                } else {
                    // Caso contrário, usa o ID da empresa do próprio usuário
                    idEmpresa = user.getEmpresa().getId();
                }

                // Armazena o ID da empresa no contexto
                TenantContext.setCurrentTenant(idEmpresa);
            }

            filterChain.doFilter(request, response);
        } finally {
            // Limpa o contexto após a requisição para evitar vazamento entre threads
            TenantContext.clear();
        }
    }
}
