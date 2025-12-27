package com.hubunity.core.domain.localidade.pais;

import com.hubunity.core.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "pais", schema = "localidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pais extends BaseEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

}
