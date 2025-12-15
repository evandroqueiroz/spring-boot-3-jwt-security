package com.hubunity.core.domain.unidademedida;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dic_unidade_medida", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nome", length = 50)
    private String nome;

}
