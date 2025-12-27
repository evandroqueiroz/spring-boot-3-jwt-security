package com.hubunity.core.domain.localidade.pais;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaisRequest {
    private String nome;
    private String sigla;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PaisResponse {
    private String id;
    private String nome;
    private String sigla;
}
