package com.alibou.core.domain.geografia;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_distritos", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Distrito {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_municipio", nullable = false)
  private Municipio municipio;

  @Column(name = "nome", length = 150, nullable = false)
  private String nome;
}
