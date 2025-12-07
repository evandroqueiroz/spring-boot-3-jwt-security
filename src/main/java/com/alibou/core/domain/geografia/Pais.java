package com.alibou.core.domain.geografia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "gg_pais", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pais {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @Column(name = "nome", length = 150)
  private String nome;

  @Column(name = "sigla", length = 2)
  private String sigla;
}
