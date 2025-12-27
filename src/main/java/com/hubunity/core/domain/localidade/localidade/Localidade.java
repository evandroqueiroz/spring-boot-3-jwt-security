package com.hubunity.core.domain.localidade.localidade;

import com.hubunity.core.domain.localidade.logradouro.Logradouro;
import com.hubunity.core.domain.localidade.municipio.Municipio;
import com.hubunity.core.domain.localidade.pais.Pais;
import com.hubunity.core.domain.localidade.tipologradouro.TipoLogradouro;
import com.hubunity.core.domain.localidade.bairro.Bairro;
import com.hubunity.core.domain.localidade.cep.Cep;
import com.hubunity.core.domain.localidade.distrito.Distrito;
import com.hubunity.core.domain.localidade.estado.Estado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gg_localidade", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localidade {

  @Id
  @Column(length = 38, nullable = false, unique = true, updatable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_pais", nullable = false)
  private Pais pais;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_estado", nullable = false)
  private Estado estado;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_municipio", nullable = false)
  private Municipio municipio;

  @ManyToOne
  @JoinColumn(name = "id_distrito")
  private Distrito distrito;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_bairro", nullable = false)
  private Bairro bairro;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_tipo_logradouro", nullable = false)
  private TipoLogradouro tipoLogradouro;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_logradouro", nullable = false)
  private Logradouro logradouro;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_cep", nullable = false)
  private Cep cep;

  @Column(name = "numero", length = 20)
  private String numero;

  @Column(name = "complemento", length = 150)
  private String complemento;
}
