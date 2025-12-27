package com.hubunity.core.domain.core.dicsituacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SituacaoRequest {

    private String id;
    private String nome;
    private String descricao;

}
