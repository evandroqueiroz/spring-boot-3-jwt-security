package com.hubunity.core.common.audit;

public interface AuditableEntity {
  String getAuditOldState();

  void setAuditOldState(String state);

  String getId(); // Ensure we can get ID
}
