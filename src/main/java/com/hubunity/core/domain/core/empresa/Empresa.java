package com.hubunity.core.domain.core.empresa;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.localidade.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_situacao", nullable = false)
    private Situacao situacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_localidade", nullable = false)
    private Endereco endereco;

    @Column(name = "razao_social", length = 255, nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Column(name = "documento", length = 20, nullable = false)
    private String documento;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

}
