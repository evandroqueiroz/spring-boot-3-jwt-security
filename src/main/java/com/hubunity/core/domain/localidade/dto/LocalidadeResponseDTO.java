package com.hubunity.core.domain.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadeResponseDTO {
  private String id;
  private PaisResponseDTO pais;
  private EstadoResponseDTO estado;
  private MunicipioResponseDTO municipio;
  private DistritoResponseDTO distrito;
  private BairroResponseDTO bairro;
  private TipoLogradouroResponseDTO tipoLogradouro;
  private LogradouroResponseDTO logradouro;
  private CepResponseDTO cep;
  private String numero;
  private String complemento;
}
