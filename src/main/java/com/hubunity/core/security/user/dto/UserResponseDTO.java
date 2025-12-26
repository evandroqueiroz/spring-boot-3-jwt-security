package com.hubunity.core.security.user.dto;

import com.hubunity.core.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  private String id;
  private String nomeAcesso;
  private Role role;
  private String idEmpresa;
  private String idPessoa;
  private String nomePessoa;
  private String idSituacao;
  private Date ultimoLogin;
  private Date createdAt;
  private Date updatedAt;
}
