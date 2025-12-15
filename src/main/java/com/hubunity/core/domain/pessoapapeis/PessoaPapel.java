package com.hubunity.core.domain.pessoapapeis;

import com.hubunity.core.domain.dicpapeis.Papel;
import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pessoa_papeis", schema = "core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaPapel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_papel", nullable = false)
    private Papel papel;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }


}
