package com.alibou.core.domain.agendamentos;

import com.alibou.core.domain.produtos.Produto;
import com.alibou.core.domain.servicos.Servico;
import com.alibou.core.domain.pessoas.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cliente", nullable = false)
  private Pessoa cliente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_profissional", nullable = false)
  private Pessoa profissional;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_servico", nullable = false)
  private Servico servico;

  @Column(name = "data_hora_inicio", nullable = false)
  private LocalDateTime dataHoraInicio;

  @Column(name = "data_hora_fim", nullable = false)
  private LocalDateTime dataHoraFim;

  @Column(name = "status", length = 20, nullable = false)
  @Builder.Default
  private String status = "PENDENTE";

  @Column(name = "preco_final")
  private Double precoFinal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_produto_vinculado")
  private Produto produtoVinculado;

  @Column(name = "observacoes", columnDefinition = "TEXT")
  private String observacoes;

  @CreatedBy
  @Column(name = "criado_por", updatable = false)
  private String criadoPor;

  @LastModifiedBy
  @Column(name = "atualizado_por")
  private String atualizadoPor;

  @CreatedDate
  @Column(name = "criado_em", nullable = false, updatable = false)
  private LocalDateTime criadoEm;

  @LastModifiedDate
  @Column(name = "atualizado_em", nullable = false)
  private LocalDateTime atualizadoEm;
}
