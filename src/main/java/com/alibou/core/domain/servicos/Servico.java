package com.alibou.core.domain.servicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "servicos", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 38)
    private String id;

    @Column(name = "id_situacao", length = 1, nullable = false)
    private String idSituacao;

    @Column(name = "codigo", insertable = false, updatable = false)
    private Long codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "preco_promocional", precision = 10, scale = 2)
    private BigDecimal precoPromocional;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @Column(name = "tempo_tolerancia_min")
    private Integer tempoToleranciaMin = 0;

}

@Entity
@Table(name = "servico_execucao", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoExecucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 38)
    private String id;

    @Column(name = "id_servico", length = 38, nullable = false)
    private String idServico;

    @Column(name = "data_execucao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExecucao;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "observacao")
    private String observacao;

    @PrePersist
    public void prePersist() {
        if (this.dataExecucao == null) {
            this.dataExecucao = new Date();
        }
    }
}

@Entity
@Table(name = "servico_produto", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 38)
    private String id;

    @Column(name = "id_servico", length = 38, nullable = false)
    private String idServico;

    @Column(name = "id_produto", length = 38, nullable = false)
    private String idProduto;

    @Column(name = "quantidade", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantidade;
}