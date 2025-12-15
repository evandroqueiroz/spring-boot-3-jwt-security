package com.hubunity.core.domain.agendamentos;

import com.hubunity.core.common.base.BaseTenantEntity;
import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.funcionarios.Funcionario;
import com.hubunity.core.domain.pessoas.Pessoa;
import com.hubunity.core.domain.servicos.Servico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "agendamentos", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento extends BaseTenantEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cliente", nullable = false)
  private Pessoa cliente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_funcionario", nullable = false)
  private Funcionario funcionario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_servico", nullable = false)
  private Servico servico;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_situacao", nullable = false)
  private Situacao situacao;

  @Column(name = "data_agendamento", nullable = false)
  private LocalDateTime dataAgendamento;

  @Column(name = "observacao", columnDefinition = "TEXT")
  private String observacao;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = new Date();
  }
}
