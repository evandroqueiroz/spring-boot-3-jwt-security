package com.alibou.core.domain.geografia;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_municipios", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_estado", nullable = false)
  private Estado estado;

  @Column(name = "nome", length = 150, nullable = false)
  private String nome;

  @Column(name = "codigo_ibge", length = 20)
  private String codigoIbge;
}
