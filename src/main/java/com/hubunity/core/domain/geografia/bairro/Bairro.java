package com.hubunity.core.domain.geografia.bairro;

import com.hubunity.core.domain.geografia.distrito.Distrito;
import com.hubunity.core.domain.geografia.municipio.Municipio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_bairros", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bairro {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_municipio", nullable = false)
  private Municipio municipio;

  @ManyToOne
  @JoinColumn(name = "id_distrito")
  private Distrito distrito;

  @Column(name = "nome", length = 150, nullable = false)
  private String nome;
}
