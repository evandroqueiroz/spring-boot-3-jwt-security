package com.hubunity.core.common.util;

import com.hubunity.core.common.context.TenantContext;

/**
 * Classe utilitária para facilitar o acesso ao ID da empresa atual
 */
public class TenantUtil {

    /**
     * Obtém o ID da empresa do contexto atual
     * @return ID da empresa ou null se não definido
     */
    public static String getCurrentEmpresaId() {
        return TenantContext.getCurrentTenant();
    }

    /**
     * Verifica se existe um contexto de empresa definido
     * @return true se existe um ID de empresa definido, false caso contrário
     */
    public static boolean hasTenantContext() {
        return TenantContext.getCurrentTenant() != null;
    }
}
