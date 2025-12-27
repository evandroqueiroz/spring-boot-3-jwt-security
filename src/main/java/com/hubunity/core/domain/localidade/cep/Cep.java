package com.hubunity.core.domain.localidade.cep;

import com.hubunity.core.domain.localidade.logradouro.Logradouro;
import com.hubunity.core.domain.localidade.bairro.Bairro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_cep", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cep {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_bairro", nullable = false)
  private Bairro bairro;

  @ManyToOne
  @JoinColumn(name = "id_logradouro")
  private Logradouro logradouro;

  @Column(name = "cep", length = 20, nullable = false)
  private String cep;
}
