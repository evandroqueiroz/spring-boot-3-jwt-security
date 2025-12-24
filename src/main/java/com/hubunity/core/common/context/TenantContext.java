package com.hubunity.core.common.context;

/**
 * Contexto de tenant para armazenar o ID da empresa atual durante a sessão.
 * Utiliza ThreadLocal para garantir isolamento entre threads/requisições.
 */
public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /**
     * Define o ID da empresa atual para o contexto da thread
     * @param idEmpresa ID da empresa
     */
    public static void setCurrentTenant(String idEmpresa) {
        currentTenant.set(idEmpresa);
    }

    /**
     * Obtém o ID da empresa atual do contexto da thread
     * @return ID da empresa ou null se não definido
     */
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    /**
     * Limpa o ID da empresa do contexto da thread
     */
    public static void clear() {
        currentTenant.remove();
    }
}
