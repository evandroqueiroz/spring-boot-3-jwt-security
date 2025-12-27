package com.hubunity.core.domain.localidade.endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDTO {
  private String id;
  private String idMunicipio;
  private String logradouro;
  private String numero;
  private String complemento;
  private String bairro;
  private String cep;
}
