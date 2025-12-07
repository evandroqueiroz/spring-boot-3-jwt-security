package com.alibou.core.domain.agendamentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoRequest {

  private String id;
  private String idCliente;
  private String idProfissional;
  private String idServico;
  private LocalDateTime dataHoraInicio;
  private LocalDateTime dataHoraFim;
  private String status;
  private Double precoFinal;
  private String idProdutoVinculado;
  private String observacoes;
}
