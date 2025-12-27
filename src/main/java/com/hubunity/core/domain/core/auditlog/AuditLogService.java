package com.hubunity.core.domain.core.auditlog;

import com.hubunity.core.common.audit.AuditService;
import com.hubunity.core.security.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService implements AuditService {

  private final AuditLogRepository repository;

  @Transactional
  public AuditLog salvar(AuditLog log) {
    return repository.save(log);
  }

  @Transactional(readOnly = true)
  public List<AuditLog> buscarPorRegistro(String nomeTabela, String idRegistro) {
    return repository.findByNomeTabelaAndIdRegistroOrderByCreatedAtDesc(nomeTabela, idRegistro);
  }

  @Override
  @Transactional
  public void registrarLog(String operacao, String nomeTabela, String idRegistro, String dadosAntes,
      String dadosDepois) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String idUsuario = "SYSTEM"; // Fallback
    String idEmpresa = "SYSTEM"; // Fallback

    if (authentication != null && authentication.getPrincipal() instanceof User user) {
      idUsuario = user.getId();
      idEmpresa = user.getEmpresa() != null ? user.getEmpresa().getId() : "UNKNOWN";
    }

    String ipAddress = getClientIp();

    AuditLog log = AuditLog.builder()
        .idEmpresa(idEmpresa)
        .idUsuario(idUsuario)
        .operacao(operacao)
        .nomeTabela(nomeTabela)
        .idRegistro(idRegistro)
        .dadosAntes(dadosAntes)
        .dadosDepois(dadosDepois)
        .enderecoIp(ipAddress)
        .build();

    repository.save(log);
  }

  private String getClientIp() {
    if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
      HttpServletRequest request = attributes.getRequest();
      String xForwardedFor = request.getHeader("X-Forwarded-For");
      if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
        return xForwardedFor.split(",")[0];
      }
      return request.getRemoteAddr();
    }
    return "UNKNOWN";
  }
}
