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

@WebMvcTest(MunicipioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MunicipioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MunicipioService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfMunicipios() throws Exception {
    Municipio municipio = Municipio.builder().id("1").nome("São Paulo").build();
    when(service.buscarTodos()).thenReturn(List.of(municipio));

    mockMvc.perform(get("/municipios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("São Paulo"));
  }

  @Test
  public void shouldReturnMunicipioById() throws Exception {
    Municipio municipio = Municipio.builder().id("1").nome("São Paulo").build();
    when(service.buscarPorId("1")).thenReturn(municipio);

    mockMvc.perform(get("/municipios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("São Paulo"));
  }

  @Test
  public void shouldSaveMunicipio() throws Exception {
    Municipio municipio = Municipio.builder().id("1").nome("São Paulo").build();
    when(service.salvar(any(Municipio.class))).thenReturn(municipio);

    mockMvc.perform(post("/municipios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(municipio)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("São Paulo"));
  }

  @Test
  public void shouldDeleteMunicipio() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/municipios/1"))
        .andExpect(status().isNoContent());
  }
}
