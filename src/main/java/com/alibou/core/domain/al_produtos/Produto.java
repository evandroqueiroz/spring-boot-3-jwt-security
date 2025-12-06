package com.alibou.core.domain.al_produtos;

import com.alibou.core.domain.situacao.Situacao;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "al_produtos", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "categoria", length = 255, nullable = false)
    private String categoria;

    @Column(name = "preco_compra", nullable = false)
    private double precoCompra;

    @Column(name = "preco_venda")
    private double precoVenda;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_situacao", nullable = false)
    private Situacao situacao;

}
