package com.alibou.core.domain.al_servicos;

import com.alibou.core.domain.situacao.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "al_servicos", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "categoria", length = 255, nullable = false)
    private String categoria;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracao_minutos;

    @Column(name = "preco")
    private double preco;


    @Column(name = "descricao", length = 255)
        private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_situacao", nullable = false)
    private Situacao situacao;
}
