package com.hubunity.core.domain.recursoshumanos.funcionarios;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRequest {

    @NotBlank(message = "ID da empresa é obrigatório")
    private String idEmpresa;

    @NotBlank(message = "ID da pessoa é obrigatório")
    private String idPessoa;

    @NotBlank(message = "Função é obrigatório")
    private String funcao;

    @NotBlank(message = "Salário é obrigatório")
    private BigDecimal salario;

    @NotBlank(message = "Data de admissão é obrigatório")
    private Date dataAdmissao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FuncionarioResponse {
    private String id;
    private String idEmpresa;
    private String idPessoa;
    private String funcao;
    private BigDecimal salario;
    private Date dataAdmissao;
}


