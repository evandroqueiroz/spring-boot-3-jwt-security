package com.alibou.core.domain.al_produtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProdutoRequest {

    private String id;
    private String id_situacao;
    private String nome;
    private String descricao;
    private String categoria;
    private double precoCompra;
    private double precoVenda;

}
