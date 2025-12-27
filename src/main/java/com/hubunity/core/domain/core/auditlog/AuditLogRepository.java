package com.hubunity.core.domain.core.auditlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
  List<AuditLog> findByNomeTabelaAndIdRegistroOrderByCreatedAtDesc(String nomeTabela, String idRegistro);
}
