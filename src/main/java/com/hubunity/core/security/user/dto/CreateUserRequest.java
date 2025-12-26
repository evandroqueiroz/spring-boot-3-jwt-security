package com.hubunity.core.security.user.dto;

import com.hubunity.core.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

  private String idEmpresa;
  private String idPessoa;
  private String idSituacao;
  private String nomeAcesso;
  private String senha;
  private Role role;
}
