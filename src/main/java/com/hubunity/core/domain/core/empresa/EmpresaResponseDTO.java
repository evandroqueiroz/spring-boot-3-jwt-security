package com.hubunity.core.domain.core.empresa;

import com.hubunity.core.domain.localidade.dto.LocalidadeResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponseDTO {
  private String id;
  private String idSituacao;
  private LocalidadeResponseDTO localidade;
  private String razaoSocial;
  private String nomeFantasia;
  private String documento;
  private String email;
  private String telefone;
  private Date createdAt;
  private Date updatedAt;
}
