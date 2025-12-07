package com.alibou.core.domain.pessoas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

  private String id;
  private String idEmpresa;
  private String tipoPessoa;
  private String nomeCompleto;
  private String razaoSocial;
  private String nomeFantasia;
  private String email;
  private String telefone;
  private String celular;
  private String documento;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;
}
