package com.hubunity.core.domain.cotacoes;

import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.fornecedores.Fornecedor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cotacao", schema = "compras")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cotacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", nullable = false)
  private Empresa empresa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_fornecedor", nullable = false)
  private Fornecedor fornecedor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_situacao", nullable = false)
  private Situacao situacao;

  @Column(name = "data_cotacao", nullable = false)
  private LocalDate dataCotacao;

  @Column(name = "observacao", length = 255)
  private String observacao;

  @OneToMany(mappedBy = "cotacao", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<CotacaoItem> itens = new ArrayList<>();

  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = new Date();
    this.updatedAt = new Date();
    if (this.dataCotacao == null) {
      this.dataCotacao = LocalDate.now();
    }
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = new Date();
  }
}
