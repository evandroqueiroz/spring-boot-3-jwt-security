package com.hubunity.core.domain.recursoshumanos.funcionarios.pausas;

import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.recursoshumanos.funcionarios.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "funcionario_pausas", schema = "recursos_humanos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioPausa {

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

  @Column(name = "tipo", nullable = false, length = 20)
  private String tipo;

  @Column(name = "horario_inicio", nullable = false)
  private LocalTime horarioInicio;

  @Column(name = "horario_fim", nullable = false)
  private LocalTime horarioFim;

  @Column(name = "descricao", length = 200)
  private String descricao;

  @Column(name = "ativo")
  private Boolean ativo = true;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

}
