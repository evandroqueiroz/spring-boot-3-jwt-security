package com.hubunity.core.domain.localidade.municipio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioRequest {
  private String idEstado;
  private String nome;
  private String codigoIbge;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class MunicipioResponse {
  private String id;
  private String idEstado;
  private String nome;
  private String codigoIbge;
}
