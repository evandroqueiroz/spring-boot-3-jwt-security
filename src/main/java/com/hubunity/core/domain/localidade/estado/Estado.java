package com.hubunity.core.domain.localidade.estado;

import com.hubunity.core.common.base.BaseEntity;
import com.hubunity.core.domain.localidade.pais.Pais;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "estado", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Estado extends BaseEntity {

    @JoinColumn(name = "id_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

}
