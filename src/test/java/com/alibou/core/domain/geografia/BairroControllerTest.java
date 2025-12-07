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

@WebMvcTest(BairroController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BairroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BairroService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfBairros() throws Exception {
    Bairro bairro = Bairro.builder().id("1").nome("Centro").build();
    when(service.buscarTodos()).thenReturn(List.of(bairro));

    mockMvc.perform(get("/bairros"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Centro"));
  }

  @Test
  public void shouldReturnBairroById() throws Exception {
    Bairro bairro = Bairro.builder().id("1").nome("Centro").build();
    when(service.buscarPorId("1")).thenReturn(bairro);

    mockMvc.perform(get("/bairros/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Centro"));
  }

  @Test
  public void shouldSaveBairro() throws Exception {
    Bairro bairro = Bairro.builder().id("1").nome("Centro").build();
    when(service.salvar(any(Bairro.class))).thenReturn(bairro);

    mockMvc.perform(post("/bairros")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bairro)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Centro"));
  }

  @Test
  public void shouldDeleteBairro() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/bairros/1"))
        .andExpect(status().isNoContent());
  }
}
