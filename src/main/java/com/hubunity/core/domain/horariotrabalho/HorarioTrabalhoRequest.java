package com.hubunity.core.domain.horariotrabalho;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioTrabalhoRequest {

  @NotBlank(message = "ID da empresa é obrigatório")
  private String idEmpresa;

  @NotBlank(message = "ID do funcionário é obrigatório")
  private String idFuncionario;

  @NotNull(message = "Dia da semana é obrigatório")
  @Min(0)
  @Max(6)
  private Integer diaSemana;

  @NotNull(message = "Hora de início é obrigatória")
  private LocalTime horaInicio;

  @NotNull(message = "Hora de fim é obrigatória")
  private LocalTime horaFim;

  private LocalTime intervaloInicio;
  private LocalTime intervaloFim;

  private Boolean ativo = true;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class HorarioTrabalhoResponse {
  private String id;
  private String idEmpresa;
  private String idFuncionario;
  private String nomeFuncionario;
  private Integer diaSemana;
  private LocalTime horaInicio;
  private LocalTime horaFim;
  private LocalTime intervaloInicio;
  private LocalTime intervaloFim;
  private Boolean ativo;
}
