package com.alibou.core.security.auth;

import com.alibou.core.security.config.JwtService;
import com.alibou.core.security.token.Token;
import com.alibou.core.security.token.TokenRepository;
import com.alibou.core.security.token.TokenType;
import com.alibou.core.security.user.Role;
import com.alibou.core.security.user.User;
import com.alibou.core.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

    // Adicione essas dependências na classe AuthenticationService:
    @Autowired
    private GoogleOAuthService googleOAuthService;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
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
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

    public boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    /**
     * Autenticação via Google OAuth
     */
    public AuthenticationResponse authenticateWithGoogle(GoogleAuthRequest request) {
        try {

            // 1. Validar o token do Google
            var payload = googleOAuthService.verifyGoogleToken(request.getToken());

            // 2. Extrair informações do usuário
            var googleUserInfo = googleOAuthService.extractUserInfo(payload);

            if (!googleUserInfo.isEmailVerified()) {
                throw new IllegalArgumentException("Email não verificado pelo Google");
            }


            // 3. Buscar ou criar usuário
            var user = repository.findByEmail(googleUserInfo.getEmail())
                    .orElseGet(() -> createUserFromGoogle(googleUserInfo));

            // 4. Gerar tokens JWT
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            // 5. Revogar tokens antigos e salvar novos
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException("Token do Google inválido - Erro de segurança");
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao comunicar com servidores do Google");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar autenticação com Google", e);
        }
    }

    /**
     * Cria um novo usuário a partir das informações do Google
     */
    private User createUserFromGoogle(GoogleUserInfo googleUserInfo) {

        // Determinar firstname e lastname
        String firstname = googleUserInfo.getGivenName() != null
                ? googleUserInfo.getGivenName()
                : googleUserInfo.getName();

        String lastname = googleUserInfo.getFamilyName() != null
                ? googleUserInfo.getFamilyName()
                : "";

        // Se firstname ainda estiver nulo, usar parte do email
        if (firstname == null || firstname.isEmpty()) {
            firstname = googleUserInfo.getEmail().split("@")[0];
        }

        var user = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(googleUserInfo.getEmail())
                .password(passwordEncoder.encode(UUID.randomUUID().toString())) // Senha aleatória
                .role(Role.USER) // Ajuste conforme seu enum: USER, CUSTOMER, etc
                .build();

        var savedUser = repository.save(user);

        return savedUser;
    }

}
