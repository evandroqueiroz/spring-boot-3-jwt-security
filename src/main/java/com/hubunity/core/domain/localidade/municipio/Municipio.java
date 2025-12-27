package com.hubunity.core.domain.localidade.municipio;

import com.hubunity.core.common.base.BaseEntity;
import com.hubunity.core.domain.localidade.estado.Estado;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "municipio", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Municipio extends BaseEntity {

  @JoinColumn(name = "id_estado", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Estado estado;

  @Column(name = "nome", nullable = false, length = 150)
  private String nome;

  @Column(name = "codigo_ibge", length = 7, unique = true)
  private String codigoIbge;

}
