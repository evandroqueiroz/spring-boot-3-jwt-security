package com.alibou.core.domain.empresa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "empresa", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @Column(length = 38, nullable = false, unique = true, updatable = false)
    protected String id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "documento")
    private String documento;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

}
