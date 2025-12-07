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

@WebMvcTest(TipoLogradouroController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TipoLogradouroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TipoLogradouroService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfTipoLogradouros() throws Exception {
    TipoLogradouro tipo = TipoLogradouro.builder().id("1").nome("Rua").build();
    when(service.buscarTodos()).thenReturn(List.of(tipo));

    mockMvc.perform(get("/tipos-logradouro"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Rua"));
  }

  @Test
  public void shouldReturnTipoLogradouroById() throws Exception {
    TipoLogradouro tipo = TipoLogradouro.builder().id("1").nome("Rua").build();
    when(service.buscarPorId("1")).thenReturn(tipo);

    mockMvc.perform(get("/tipos-logradouro/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Rua"));
  }

  @Test
  public void shouldSaveTipoLogradouro() throws Exception {
    TipoLogradouro tipo = TipoLogradouro.builder().id("1").nome("Rua").build();
    when(service.salvar(any(TipoLogradouro.class))).thenReturn(tipo);

    mockMvc.perform(post("/tipos-logradouro")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(tipo)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Rua"));
  }

  @Test
  public void shouldDeleteTipoLogradouro() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/tipos-logradouro/1"))
        .andExpect(status().isNoContent());
  }
}
