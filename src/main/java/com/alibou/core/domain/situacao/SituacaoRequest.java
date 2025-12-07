package com.alibou.core.domain.situacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SituacaoRequest {

    private String id;
    private String nome;
    private String descricao;

}
