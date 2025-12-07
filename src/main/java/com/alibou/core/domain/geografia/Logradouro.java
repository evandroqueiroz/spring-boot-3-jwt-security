package com.alibou.core.domain.geografia;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_logradouros", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Logradouro {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_bairro", nullable = false)
  private Bairro bairro;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_tipo_logradouro", nullable = false)
  private TipoLogradouro tipoLogradouro;

  @Column(name = "nome", length = 150, nullable = false)
  private String nome;
}
