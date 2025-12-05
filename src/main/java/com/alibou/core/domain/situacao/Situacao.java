package com.alibou.core.domain.situacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "situacao", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Situacao {

    @Id
    @Column(length = 1, nullable = false)
    private String id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 255, nullable = false)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

}
