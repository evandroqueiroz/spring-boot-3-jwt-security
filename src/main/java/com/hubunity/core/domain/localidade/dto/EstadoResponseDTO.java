package com.hubunity.core.domain.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoResponseDTO {
  private String id;
  private PaisResponseDTO pais;
  private String nome;
  private String sigla;
}
