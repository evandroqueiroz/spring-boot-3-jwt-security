package com.alibou.core.domain.geografia;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaisController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for unit test
public class PaisControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaisService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldReturnListOfPaises() throws Exception {
    Pais pais = Pais.builder().id("1").nome("Brasil").sigla("BR").build();
    when(service.buscarTodos()).thenReturn(List.of(pais));

    mockMvc.perform(get("/paises"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Brasil"))
        .andExpect(jsonPath("$[0].sigla").value("BR"));
  }

  @Test
  public void shouldReturnPaisById() throws Exception {
    Pais pais = Pais.builder().id("1").nome("Brasil").sigla("BR").build();
    when(service.buscarPorId("1")).thenReturn(pais);

    mockMvc.perform(get("/paises/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Brasil"));
  }

  @Test
  public void shouldSavePais() throws Exception {
    Pais pais = Pais.builder().id("1").nome("Brasil").sigla("BR").build();
    when(service.salvar(any(Pais.class))).thenReturn(pais);

    mockMvc.perform(post("/paises")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(pais)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Brasil"));
  }

  @Test
  public void shouldDeletePais() throws Exception {
    doNothing().when(service).deletar("1");

    mockMvc.perform(delete("/paises/1"))
        .andExpect(status().isNoContent());
  }
}
