package com.hubunity.core.domain.recursoshumanos.funcionarios.folgas;

import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.recursoshumanos.funcionarios.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "funcionario_folgas", schema = "recursos_humanos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFolga {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", nullable = false)
  private Empresa empresa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_funcionario", nullable = false)
  private Funcionario funcionario;

  @Column(name = "data_inicio", nullable = false)
  private LocalDate dataInicio;

  @Column(name = "data_fim", nullable = false)
  private LocalDate dataFim;

  @Column(name = "motivo", nullable = false, length = 200)
  private String motivo;

  @Column(name = "tipo", length = 50)
  private String tipo;

  @Column(name = "aprovado")
  private Boolean aprovado = false;

  @Column(name = "observacoes", columnDefinition = "TEXT")
  private String observacoes;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

}
