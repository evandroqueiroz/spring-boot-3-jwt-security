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

@WebMvcTest(DistritoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DistritoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DistritoService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfDistritos() throws Exception {
    Distrito distrito = Distrito.builder().id("1").nome("Distrito Central").build();
    when(service.buscarTodos()).thenReturn(List.of(distrito));

    mockMvc.perform(get("/distritos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Distrito Central"));
  }

  @Test
  public void shouldReturnDistritoById() throws Exception {
    Distrito distrito = Distrito.builder().id("1").nome("Distrito Central").build();
    when(service.buscarPorId("1")).thenReturn(distrito);

    mockMvc.perform(get("/distritos/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Distrito Central"));
  }

  @Test
  public void shouldSaveDistrito() throws Exception {
    Distrito distrito = Distrito.builder().id("1").nome("Distrito Central").build();
    when(service.salvar(any(Distrito.class))).thenReturn(distrito);

    mockMvc.perform(post("/distritos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(distrito)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Distrito Central"));
  }

  @Test
  public void shouldDeleteDistrito() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/distritos/1"))
        .andExpect(status().isNoContent());
  }
}
