package com.alibou.core.domain.al_servicos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServicoRequest {

    private String id;
    private String id_situacao;
    private String nome;
    private String categoria;
    private Integer duracao_minutos;
    private double preco;
    private String descricao;

}
