package com.hubunity.core.domain.funcionarios.folgas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFolgaRequest {

  @NotBlank(message = "ID da empresa é obrigatório")
  private String idEmpresa;

  @NotBlank(message = "ID do funcionário é obrigatório")
  private String idFuncionario;

  @NotNull(message = "Data de início é obrigatória")
  private LocalDate dataInicio;

  @NotNull(message = "Data de fim é obrigatória")
  private LocalDate dataFim;

  @NotBlank(message = "Motivo é obrigatório")
  private String motivo;

  private String tipo;

  private Boolean aprovado = false;

  private String observacoes;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FuncionarioFolgaResponse {
  private String id;
  private String idEmpresa;
  private String idFuncionario;
  private LocalDate dataInicio;
  private LocalDate dataFim;
  private String motivo;
  private String tipo;
  private Boolean aprovado;
  private String observacoes;
}
