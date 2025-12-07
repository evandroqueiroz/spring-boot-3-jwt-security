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

@WebMvcTest(EstadoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EstadoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EstadoService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfEstados() throws Exception {
    Estado estado = Estado.builder().id("1").nome("São Paulo").sigla("SP").build();
    when(service.buscarTodos()).thenReturn(List.of(estado));

    mockMvc.perform(get("/estados"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("São Paulo"));
  }

  @Test
  public void shouldReturnEstadoById() throws Exception {
    Estado estado = Estado.builder().id("1").nome("São Paulo").sigla("SP").build();
    when(service.buscarPorId("1")).thenReturn(estado);

    mockMvc.perform(get("/estados/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("São Paulo"));
  }

  @Test
  public void shouldSaveEstado() throws Exception {
    Estado estado = Estado.builder().id("1").nome("São Paulo").sigla("SP").build();
    when(service.salvar(any(Estado.class))).thenReturn(estado);

    mockMvc.perform(post("/estados")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(estado)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("São Paulo"));
  }

  @Test
  public void shouldDeleteEstado() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/estados/1"))
        .andExpect(status().isNoContent());
  }
}
