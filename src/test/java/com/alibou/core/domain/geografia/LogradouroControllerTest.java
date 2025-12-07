package com.alibou.core.domain.geografia;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogradouroController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LogradouroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LogradouroService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfLogradouros() throws Exception {
    Logradouro logradouro = Logradouro.builder().id("1").nome("Av. Paulista").build();
    when(service.buscarTodos()).thenReturn(List.of(logradouro));

    mockMvc.perform(get("/logradouros"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Av. Paulista"));
  }

  @Test
  public void shouldReturnLogradouroById() throws Exception {
    Logradouro logradouro = Logradouro.builder().id("1").nome("Av. Paulista").build();
    when(service.buscarPorId("1")).thenReturn(logradouro);

    mockMvc.perform(get("/logradouros/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Av. Paulista"));
  }

  @Test
  public void shouldSaveLogradouro() throws Exception {
    Logradouro logradouro = Logradouro.builder().id("1").nome("Av. Paulista").build();
    when(service.salvar(any(Logradouro.class))).thenReturn(logradouro);

    mockMvc.perform(post("/logradouros")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(logradouro)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Av. Paulista"));
  }

  @Test
  public void shouldDeleteLogradouro() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/logradouros/1"))
        .andExpect(status().isNoContent());
  }
}
