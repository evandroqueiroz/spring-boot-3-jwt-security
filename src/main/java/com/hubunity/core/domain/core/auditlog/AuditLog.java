package com.hubunity.core.domain.core.auditlog;

import com.hubunity.core.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ExcludeSuperclassListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ExcludeSuperclassListeners
@Table(name = "audit_log", schema = "core")
public class AuditLog extends BaseEntity {

  @Column(name = "id_empresa", nullable = false, length = 38)
  private String idEmpresa;

  @Column(name = "id_usuario", nullable = false, length = 38)
  private String idUsuario;

  @Column(name = "operacao", nullable = false, length = 10)
  private String operacao;

  @Column(name = "nome_tabela", nullable = false, length = 100)
  private String nomeTabela;

  @Column(name = "id_registro", nullable = false, length = 38)
  private String idRegistro;

  @Column(name = "dados_antes", columnDefinition = "jsonb")
  private String dadosAntes;

  @Column(name = "dados_depois", columnDefinition = "jsonb")
  private String dadosDepois;

  @Column(name = "endereco_ip", length = 60)
  private String enderecoIp;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
