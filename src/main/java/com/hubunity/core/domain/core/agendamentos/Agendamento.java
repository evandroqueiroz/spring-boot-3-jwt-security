package com.hubunity.core.domain.core.agendamentos;

import com.hubunity.core.domain.core.clientes.Cliente;
import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.recursoshumanos.funcionarios.Funcionario;
import com.hubunity.core.domain.core.servicos.Servico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "agendamentos", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Agendamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", nullable = false)
  private Empresa empresa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cliente", nullable = false)
  private Cliente cliente;

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
