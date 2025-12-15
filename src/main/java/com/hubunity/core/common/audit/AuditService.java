package com.hubunity.core.common.audit;

public interface AuditService {
  void registrarLog(String operacao, String nomeTabela, String idRegistro, String dadosAntes, String dadosDepois);
}
