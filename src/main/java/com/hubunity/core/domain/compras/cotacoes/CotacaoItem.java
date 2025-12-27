package com.hubunity.core.domain.compras.cotacoes;

import com.hubunity.core.domain.core.produtos.Produto;
import com.hubunity.core.domain.core.servicos.Servico;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cotacao_item", schema = "compras")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotacaoItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cotacao", nullable = false)
  private Cotacao cotacao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_produto")
  private Produto produto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_servico")
  private Servico servico;

  @Column(name = "quantidade", precision = 12, scale = 3)
  private BigDecimal quantidade;

  @Column(name = "valor_unitario", precision = 12, scale = 2, nullable = false)
  private BigDecimal valorUnitario;
}
