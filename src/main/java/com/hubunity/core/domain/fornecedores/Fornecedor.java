package com.hubunity.core.domain.fornecedores;

import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "fornecedores", schema = "core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Fornecedor {

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

  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Column(name = "updated_at")
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
