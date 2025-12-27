package com.hubunity.core.domain.localidade.endereco;

import com.hubunity.core.common.base.BaseEntity;
import com.hubunity.core.domain.localidade.municipio.Municipio;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "endereco", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Endereco extends BaseEntity {


  @JoinColumn(name = "id_municipio", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Municipio municipio;

  @Column(name = "logradouro", nullable = false)
  private String logradouro;

  @Column(name = "numero", length = 20)
  private String numero;

  @Column(name = "complemento")
  private String complemento;

  @Column(name = "bairro", length = 100)
  private String bairro;

  @Column(name = "cep", length = 9)
  private String cep;

}
