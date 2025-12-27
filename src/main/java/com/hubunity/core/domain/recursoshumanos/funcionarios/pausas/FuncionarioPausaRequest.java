package com.hubunity.core.domain.recursoshumanos.funcionarios.pausas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioPausaRequest {

  @NotBlank(message = "ID da empresa é obrigatório")
  private String idEmpresa;

  @NotBlank(message = "ID do funcionário é obrigatório")
  private String idFuncionario;

  @NotBlank(message = "Tipo é obrigatório")
  private String tipo;

  @NotNull(message = "Horário de início é obrigatório")
  private LocalTime horarioInicio;

  @NotNull(message = "Horário de fim é obrigatório")
  private LocalTime horarioFim;

  private String descricao;

  private Boolean ativo = true;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FuncionarioPausaResponse {
  private String id;
  private String idEmpresa;
  private String idFuncionario;
  private String tipo;
  private LocalTime horarioInicio;
  private LocalTime horarioFim;
  private String descricao;
  private Boolean ativo;
}
