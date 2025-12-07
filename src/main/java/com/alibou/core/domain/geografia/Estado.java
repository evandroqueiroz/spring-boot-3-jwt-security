package com.alibou.core.domain.geografia;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_estados", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne
  @JoinColumn(name = "id_pais")
  private Pais pais;

  @Column(name = "nome", length = 150)
  private String nome;

  @Column(name = "sigla", length = 2)
  private String sigla;
}
