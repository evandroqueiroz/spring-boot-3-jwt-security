package com.hubunity.core.domain.empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRequest {

    private String idSituacao = "A";
    private String idPessoa;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class EmpresaResponse {
    private String id;
    private String idSituacao;
    private String idPessoa;
    private Date createdAt;
    private Date updatedAt;
}
