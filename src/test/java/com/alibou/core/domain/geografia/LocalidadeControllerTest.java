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

@WebMvcTest(LocalidadeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LocalidadeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LocalidadeService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfLocalidades() throws Exception {
    Localidade localidade = Localidade.builder().id("1").numero("123").build();
    when(service.buscarTodos()).thenReturn(List.of(localidade));

    mockMvc.perform(get("/localidades"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].numero").value("123"));
  }

  @Test
  public void shouldReturnLocalidadeById() throws Exception {
    Localidade localidade = Localidade.builder().id("1").numero("123").build();
    when(service.buscarPorId("1")).thenReturn(localidade);

    mockMvc.perform(get("/localidades/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.numero").value("123"));
  }

  @Test
  public void shouldSaveLocalidade() throws Exception {
    Localidade localidade = Localidade.builder().id("1").numero("123").build();
    when(service.salvar(any(Localidade.class))).thenReturn(localidade);

    mockMvc.perform(post("/localidades")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(localidade)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.numero").value("123"));
  }

  @Test
  public void shouldDeleteLocalidade() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/localidades/1"))
        .andExpect(status().isNoContent());
  }
}
