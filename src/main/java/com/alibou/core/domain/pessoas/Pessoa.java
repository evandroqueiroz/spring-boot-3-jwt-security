package com.alibou.core.domain.pessoas;

import com.alibou.core.domain.empresa.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "pessoas", schema = "core")
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // <--- ADD THIS LINE
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

  @Column(name = "tipo_pessoa", length = 50, nullable = false)
  private String tipoPessoa;

  @Column(name = "nome_completo", length = 150)
  private String nomeCompleto;

  @Column(name = "razao_social", length = 150)
  private String razaoSocial;

  @Column(name = "nome_fantasia", length = 150)
  private String nomeFantasia;

  @Column(name = "email", length = 150, unique = true)
  private String email;

  @Column(name = "telefone", length = 20)
  private String telefone;

  @Column(name = "celular", length = 20)
  private String celular;

  @Column(name = "documento", length = 20, nullable = false, unique = true)
  private String documento;

  @Column(name = "data_inicio", nullable = false)
  private LocalDateTime dataInicio;

  @Column(name = "data_fim")
  private LocalDateTime dataFim;
}
