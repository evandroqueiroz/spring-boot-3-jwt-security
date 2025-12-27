package com.hubunity.core.domain.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogradouroResponseDTO {
  private String id;
  private TipoLogradouroResponseDTO tipoLogradouro;
  private String nome;
}
