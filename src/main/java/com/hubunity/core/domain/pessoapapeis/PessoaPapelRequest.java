package com.hubunity.core.domain.pessoapapeis;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaPapelRequest {

    @NotBlank(message = "ID da empresa é obrigatório")
    private String idEmpresa;

    @NotBlank(message = "ID da pessoa é obrigatório")
    private String idPessoa;

    @NotBlank(message = "ID do papel é obrigatório")
    private String idPapel;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PessoaPapelResponse {
    private String id;
    private String idEmpresa;
    private String idPessoa;
    private String idPapel;
    private Date createdAt;
}