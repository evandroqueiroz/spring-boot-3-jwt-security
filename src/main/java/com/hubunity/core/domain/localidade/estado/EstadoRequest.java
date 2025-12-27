package com.hubunity.core.domain.localidade.estado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadoRequest {
    private String idPais;
    private String nome;
    private String sigla;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class EstadoResponse {
    private String id;
    private String idPais;
    private String nome;
    private String sigla;
}
