package com.hubunity.core.common.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubunity.core.common.util.BeanUtil;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditListener {

  @PostLoad
  public void postLoad(Object entity) {
    if (entity instanceof AuditableEntity ae) {
      // Avoid auditing the AuditLog itself naturally, but explicit check:
      if (entity.getClass().getName().contains("AuditLog"))
        return;
      try {
        String json = getObjectMapper().writeValueAsString(entity);
        ae.setAuditOldState(json);
      } catch (Exception e) {
        log.error("Error collecting audit old state for {}", entity.getClass().getName(), e);
      }
    }
  }

  @PostPersist
  public void postPersist(Object entity) {
    audit(entity, "insert");
  }

  @PostUpdate
  public void postUpdate(Object entity) {
    audit(entity, "update");
  }

  @PostRemove
  public void postRemove(Object entity) {
    audit(entity, "delete");
  }

  private void audit(Object entity, String operacao) {
    if (entity instanceof AuditableEntity ae) {
      if (entity.getClass().getName().contains("AuditLog"))
        return;

      try {
        AuditService auditService = BeanUtil.getBean(AuditService.class);
        if (auditService == null) {
          log.warn("AuditService bean not found. Skipping audit.");
          return;
        }

        String dadosAntes = null;
        String dadosDepois = null;
        String currentJson = getObjectMapper().writeValueAsString(entity);

        if ("insert".equals(operacao)) {
          dadosDepois = currentJson;
        } else if ("update".equals(operacao)) {
          dadosAntes = ae.getAuditOldState();
          dadosDepois = currentJson;
          // Optimization: If nothing changed, maybe skip?
          // But serialization comparison is rough.
          if (dadosAntes != null && dadosAntes.equals(dadosDepois)) {
            return;
          }
        } else if ("delete".equals(operacao)) {
          dadosAntes = ae.getAuditOldState();
          if (dadosAntes == null)
            dadosAntes = currentJson; // Fallback
        }

        // Extract table name could be simpler but let's use class simple name or @Table
        // annotation
        String nomeTabela = getTableName(entity.getClass());

        auditService.registrarLog(operacao, nomeTabela, ae.getId(), dadosAntes, dadosDepois);

      } catch (Exception e) {
        log.error("Error recording audit for {} id {}", entity.getClass().getName(), ae.getId(), e);
      }
    }
  }

  private ObjectMapper getObjectMapper() {
    ObjectMapper mapper = BeanUtil.getBean(ObjectMapper.class);
    if (mapper == null)
      return new ObjectMapper();
    return mapper;
  }

  private String getTableName(Class<?> clazz) {
    Table table = clazz.getAnnotation(Table.class);
    if (table != null && !table.name().isEmpty()) {
      return table.schema().isEmpty() ? table.name() : table.schema() + "." + table.name();
    }
    return clazz.getSimpleName();
  }
}
