package com.hubunity.core.domain.pessoas;

import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "pessoas", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situacao", nullable = false)
    private Situacao situacao;

    @Column(name = "tipo_pessoa", length = 50, nullable = false)
    private String tipoPessoa;

    @Column(name = "razao_social", length = 150)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 150)
    private String nomeFantasia;

    @Column(name = "nome_completo", length = 150)
    private String nomeCompleto;

    @Column(name = "tipo_documento", length = 4)
    private String tipoDocumento;

    @Column(name = "documento", length = 20, nullable = false, unique = true)
    private String documento;

    @Column(name = "email", length = 150, unique = true)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "data_nascimento", length = 20)
    private Date dataNascimento;

    @Column(name = "created_at", length = 20)
    private Date createdAt;

    @Column(name = "updated_at", length = 20)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
