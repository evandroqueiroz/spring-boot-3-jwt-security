package com.hubunity.core.domain.unidademedida;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeMedidaRequest {

    @NotBlank(message = "Código da unidade de medida é obrigatório")
    private String codigo;

    @NotBlank(message = "Nome da unidade de medida é obrigatório")
    private String nome;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UnidadeMedidaResponse {
    private String id;
    private String codigo;
    private String nome;
}