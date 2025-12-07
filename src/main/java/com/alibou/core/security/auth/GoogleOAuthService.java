package com.alibou.core.security.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthService {

    @Value("${google.client.id}")
    private String googleClientId;

    /**
     * Valida o token do Google e retorna as informações do usuário
     */
    public GoogleIdToken.Payload verifyGoogleToken(String idTokenString)
            throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory()
        )
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Log das informações recebidas
            log.info("Google authentication successful for email: {}", payload.getEmail());

            return payload;
        } else {
            log.error("Invalid Google ID token");
            throw new IllegalArgumentException("Invalid Google ID token");
        }
    }

    /**
     * Extrai informações do usuário do payload do Google
     */
    public GoogleUserInfo extractUserInfo(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");

        return GoogleUserInfo.builder()
                .email(email)
                .emailVerified(emailVerified)
                .name(name)
                .pictureUrl(pictureUrl)
                .givenName(givenName)
                .familyName(familyName)
                .build();
    }
}