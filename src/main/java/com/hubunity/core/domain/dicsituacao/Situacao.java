package com.hubunity.core.domain.dicsituacao;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "dic_situacao", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Situacao {

    @Id
    @Column(length = 1, nullable = false)
    private String id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 255, nullable = false)
    private String descricao;

}
