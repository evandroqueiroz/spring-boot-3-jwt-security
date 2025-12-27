package com.hubunity.core.domain.core.horariotrabalho;

import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.recursoshumanos.funcionarios.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "horario_trabalho", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioTrabalho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", nullable = false)
  private Empresa empresa;

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

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

}
