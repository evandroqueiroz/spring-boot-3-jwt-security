package com.hubunity.core.security.auth;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.core.pessoas.Pessoa;
import com.hubunity.core.security.config.JwtService;
import com.hubunity.core.security.token.Token;
import com.hubunity.core.security.token.TokenRepository;
import com.hubunity.core.security.token.TokenType;
import com.hubunity.core.security.user.User;
import com.hubunity.core.security.user.UserRepository;
import com.hubunity.core.security.user.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final String MASTER_COMPANY_ID = "f008f749-28a6-41ad-953a-e9c2d14a7fdacc";

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        Empresa empresa = entityManager.getReference(
                Empresa.class,
                request.getIdEmpresa()
        );

        Pessoa pessoa = entityManager.getReference(
                Pessoa.class,
                request.getIdPessoa()
        );

        Situacao situacao = entityManager.getReference(
                Situacao.class,
                request.getIdSituacao()
        );

        var user = User.builder()
                .empresa(empresa)
                .pessoa(pessoa)
                .situacao(situacao)
                .nomeAcesso(request.getNomeAcesso())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .build();

        var savedUser = repository.save(user);

        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);

        String idEmpresa = savedUser.getRole() == Role.ADMIN
                ? MASTER_COMPANY_ID
                : savedUser.getEmpresa().getId();

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .idEmpresa(idEmpresa)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNomeAcesso(),
                        request.getSenha()
                )
        );
        var user = repository.findByNomeAcesso(request.getNomeAcesso())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        String idEmpresa = user.getRole() == Role.ADMIN
                ? MASTER_COMPANY_ID
                : user.getEmpresa().getId();

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .idEmpresa(idEmpresa)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userNomeAcesso;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userNomeAcesso = jwtService.extractUsername(refreshToken);
        if (userNomeAcesso != null) {
            var user = this.repository.findByNomeAcesso(userNomeAcesso)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                String idEmpresa = user.getRole() == Role.ADMIN
                        ? MASTER_COMPANY_ID
                        : user.getEmpresa().getId();

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .idEmpresa(idEmpresa)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public boolean userExists(String nomeAcesso) {
        return repository.findByNomeAcesso(nomeAcesso).isPresent();
    }
}
