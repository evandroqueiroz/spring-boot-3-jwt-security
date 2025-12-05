package com.alibou.core.domain.filial;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "filial", schema = "core")
@Getter
@Setter
public class Filial {

    @Id
    @Column(length = 38, nullable = false, unique = true, updatable = false)
    protected String id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

}
