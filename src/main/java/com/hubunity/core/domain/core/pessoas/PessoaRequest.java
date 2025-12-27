package com.hubunity.core.domain.core.pessoas;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

    @NotBlank(message = "ID da empresa é obrigatório")
    private String idEmpresa;

    @NotBlank(message = "ID da situação é obrigatório")
    private String idSituacao = "A";

    @NotBlank(message = "Tipo é obrigatório (PF ou PJ)")
    private String tipoPessoa;

    private String razaoSocial;
    private String nomeFantasia;
    private String nomeCompleto;
    private String tipoDocumento;
    private String documento;
    private String email;
    private String telefone;
    private String celular;
    private Date dataNascimento;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PessoaResponse {
    private String id;
    private String idEmpresa;
    private String idSituacao;
    private String tipoPessoa;
    private String razaoSocial;
    private String nomeFantasia;
    private String nomeCompleto;
    private String tipoDocumento;
    private String documento;
    private String email;
    private String telefone;
    private String celular;
    private Date dataNascimento;
    private Date createdAt;
    private Date updatedAt;
}
