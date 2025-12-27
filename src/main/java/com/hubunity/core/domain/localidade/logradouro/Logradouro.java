package com.hubunity.core.domain.localidade.logradouro;

import com.hubunity.core.domain.localidade.tipologradouro.TipoLogradouro;
import com.hubunity.core.domain.localidade.bairro.Bairro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_logradouros", schema = "localidade")
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
