package com.hubunity.core.domain.agendamentos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

  @NotBlank(message = "ID da empresa é obrigatório")
  private String idEmpresa;

  @NotBlank(message = "ID do cliente é obrigatório")
  private String idCliente;

  @NotBlank(message = "ID do funcionário é obrigatório")
  private String idFuncionario;

  @NotBlank(message = "ID do serviço é obrigatório")
  private String idServico;

  // Optional: if not provided, might default to 'Agendado' or similar in Service
  private String idSituacao;

  @NotNull(message = "Data do agendamento é obrigatória")
  private LocalDateTime dataAgendamento;

  private String observacao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AgendamentoResponse {
  private String id;
  private String idEmpresa;
  private String idCliente;
  private String nomeCliente;
  private String idFuncionario;
  private String nomeFuncionario;
  private String idServico;
  private String nomeServico;
  private String idSituacao;
  private String nomeSituacao;
  private LocalDateTime dataAgendamento;
  private String observacao;
}
