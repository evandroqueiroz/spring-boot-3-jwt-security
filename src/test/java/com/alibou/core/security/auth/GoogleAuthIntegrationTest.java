package com.alibou.core.security.auth;

import com.alibou.core.security.config.JwtAuthenticationFilter;
import com.alibou.core.security.config.JwtService;
import com.alibou.core.security.config.SecurityConfiguration;
import com.alibou.core.security.token.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import({ SecurityConfiguration.class, JwtAuthenticationFilter.class })
public class GoogleAuthIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthenticationService service;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private TokenRepository tokenRepository;

  @MockBean
  private UserDetailsService userDetailsService;

  @MockBean
  private AuthenticationProvider authenticationProvider;

  @MockBean
  private LogoutHandler logoutHandler;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldAuthenticateWithGoogle_WhenEndpointIsWhitelisted() throws Exception {
    GoogleAuthRequest request = new GoogleAuthRequest("valid_google_token");

    AuthenticationResponse response = new AuthenticationResponse("access_token", "refresh_token");

    when(service.authenticateWithGoogle(any(GoogleAuthRequest.class))).thenReturn(response);

    mockMvc.perform(post("/api/v1/auth/google")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }
}
