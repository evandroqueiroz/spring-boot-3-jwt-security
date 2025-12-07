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

@WebMvcTest(CepController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CepControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CepService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfCeps() throws Exception {
    Cep cep = Cep.builder().id("1").cep("01310-100").build();
    when(service.buscarTodos()).thenReturn(List.of(cep));

    mockMvc.perform(get("/ceps"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].cep").value("01310-100"));
  }

  @Test
  public void shouldReturnCepById() throws Exception {
    Cep cep = Cep.builder().id("1").cep("01310-100").build();
    when(service.buscarPorId("1")).thenReturn(cep);

    mockMvc.perform(get("/ceps/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cep").value("01310-100"));
  }

  @Test
  public void shouldSaveCep() throws Exception {
    Cep cep = Cep.builder().id("1").cep("01310-100").build();
    when(service.salvar(any(Cep.class))).thenReturn(cep);

    mockMvc.perform(post("/ceps")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cep)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cep").value("01310-100"));
  }

  @Test
  public void shouldDeleteCep() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/ceps/1"))
        .andExpect(status().isNoContent());
  }
}
