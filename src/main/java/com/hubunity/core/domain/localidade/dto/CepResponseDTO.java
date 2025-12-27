package com.hubunity.core.domain.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CepResponseDTO {
  private String id;
  private LogradouroResponseDTO logradouro;
  private BairroResponseDTO bairro;
  private String cep;
}
