package com.hubunity.core.domain.auditlog;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

  private final AuditLogService service;

  @GetMapping
  public ResponseEntity<List<AuditLog>> buscarLogs(
      @RequestParam String nomeTabela,
      @RequestParam String idRegistro) {
    return ResponseEntity.ok(service.buscarPorRegistro(nomeTabela, idRegistro));
  }
}
