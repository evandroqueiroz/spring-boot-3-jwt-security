package com.hubunity.core.domain.produtos;

import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.unidademedida.UnidadeMedida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_situacao")
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "id_unidade_medida")
    private UnidadeMedida unidadeMedida;

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

    @Column(name = "created_at", nullable = false)
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
