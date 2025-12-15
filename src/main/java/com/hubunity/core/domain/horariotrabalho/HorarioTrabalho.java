package com.hubunity.core.domain.horariotrabalho;

import com.hubunity.core.common.base.BaseTenantEntity;
import com.hubunity.core.domain.funcionarios.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "horario_trabalho", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioTrabalho extends BaseTenantEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_funcionario", nullable = false)
  private Funcionario funcionario;

  @Column(name = "dia_semana", nullable = false)
  private Integer diaSemana; // 0=Domingo, 1=Segunda...

  @Column(name = "hora_inicio", nullable = false)
  private LocalTime horaInicio;

  @Column(name = "hora_fim", nullable = false)
  private LocalTime horaFim;

  @Column(name = "intervalo_inicio")
  private LocalTime intervaloInicio;

  @Column(name = "intervalo_fim")
  private LocalTime intervaloFim;

  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;

}
