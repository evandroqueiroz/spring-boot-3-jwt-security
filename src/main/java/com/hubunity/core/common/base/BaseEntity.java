package com.hubunity.core.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubunity.core.common.audit.AuditListener;
import com.hubunity.core.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class BaseEntity implements AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 38, nullable = false, unique = true, updatable = false)
    protected String id;

    @Transient
    @JsonIgnore
    private String auditOldState;

    @Override
    public String getAuditOldState() {
        return auditOldState;
    }

    @Override
    public void setAuditOldState(String state) {
        this.auditOldState = state;
    }
}
