package com.hubunity.core.domain.empresa;

import com.hubunity.core.domain.pessoas.Pessoa;
import com.hubunity.core.domain.dicsituacao.Situacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "empresa", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 38, nullable = false, unique = true, updatable = false)
    protected String id;

    @ManyToOne
    @JoinColumn(name = "id_situacao")
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
