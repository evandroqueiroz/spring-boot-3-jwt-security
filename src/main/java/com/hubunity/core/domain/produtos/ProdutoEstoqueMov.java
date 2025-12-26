package com.hubunity.core.domain.produtos;

import com.hubunity.core.domain.empresa.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "produto_estoque_mov", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueMov {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", length = 38)
  private String id;

  @ManyToOne
  @JoinColumn(name = "id_empresa")
  private Empresa empresa;

  @ManyToOne
  @JoinColumn(name = "id_produto")
  private Produto produto;

  @Column(name = "tipo_mov", length = 1, nullable = false)
  private String tipoMov; // E=Entrada, S=Saída, A=Ajuste

  @Column(name = "quantidade", precision = 10, scale = 2, nullable = false)
  private BigDecimal quantidade;

  @Column(name = "data_mov")
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date dataMov;

  @Column(name = "observacao")
  private String observacao;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @PrePersist
  public void prePersist() {
    if (this.dataMov == null) {
      this.dataMov = new java.util.Date();
    }
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = new Date();
  }

}
