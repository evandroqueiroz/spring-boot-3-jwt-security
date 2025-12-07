package com.alibou.core.domain.geografia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "gg_tipo_logradouro", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoLogradouro {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @Column(name = "nome", length = 150, nullable = false)
  private String nome;
}
