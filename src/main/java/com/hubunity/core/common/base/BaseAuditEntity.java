package com.hubunity.core.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseAuditEntity extends BaseTenantEntity {

    @Column(name = "criado_por", length = 38)
    protected String criadoPor;

    @Column(name = "atualizado_por", length = 38)
    protected String atualizadoPor;
}
