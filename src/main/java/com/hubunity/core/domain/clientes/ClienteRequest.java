package com.hubunity.core.domain.clientes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
  @NotBlank(message = "ID da pessoa é obrigatório")
  private String idPessoa;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ClienteResponse {
  private String id;
  private String idEmpresa;
  private String idPessoa;
  private String nomeCompleto;
  private String documento;
  private Date createdAt;
  private Date updatedAt;
}
