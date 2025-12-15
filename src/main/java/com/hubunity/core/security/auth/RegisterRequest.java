package com.hubunity.core.security.auth;

import com.hubunity.core.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String idEmpresa;
    private String idPessoa;
    private String idSituacao;
    private String nomeAcesso;
    private String senha;
    private Role role;
}
