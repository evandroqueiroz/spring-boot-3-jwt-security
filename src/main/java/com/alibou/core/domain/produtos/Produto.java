package com.alibou.core.domain.produtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 38)
    private String id;

    @Column(name = "id_situacao", length = 1, nullable = false)
    private String idSituacao = "A";

    @Column(name = "id_unidade_medida", length = 38, nullable = false)
    private String idUnidadeMedida;

    @Column(name = "codigo", insertable = false, updatable = false)
    private Long codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "preco_compra")
    private Double precoCompra;

    @Column(name = "preco_venda")
    private Double precoVenda;

    @Column(name = "estoque_minimo", precision = 10, scale = 2)
    private BigDecimal estoqueMinimo;

    @Column(name = "estoque_atual", precision = 10, scale = 2, nullable = false)
    private BigDecimal estoqueAtual = BigDecimal.ZERO;
}

@Entity
@Table(name = "produto_estoque_mov", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
class ProdutoEstoqueMov {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 38)
    private String id;

    @Column(name = "id_produto", length = 38, nullable = false)
    private String idProduto;

    @Column(name = "tipo_mov", length = 1, nullable = false)
    private String tipoMov; // E=Entrada, S=Saída, A=Ajuste

    @Column(name = "quantidade", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantidade;

    @Column(name = "data_mov")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dataMov;

    @Column(name = "observacao")
    private String observacao;

    @PrePersist
    public void prePersist() {
        if (this.dataMov == null) {
            this.dataMov = new java.util.Date();
        }
    }
}
