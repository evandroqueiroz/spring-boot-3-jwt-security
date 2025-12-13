package com.alibou.core.domain.servicos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequest {

    @NotBlank(message = "ID da situação é obrigatório")
    private String idSituacao;

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 255, message = "Categoria deve ter no máximo 255 caracteres")
    private String categoria;

    @DecimalMin(value = "0.0", message = "Preço deve ser positivo")
    private BigDecimal preco;

    @DecimalMin(value = "0.0", message = "Preço promocional deve ser positivo")
    private BigDecimal precoPromocional;

    @NotNull(message = "Duração em minutos é obrigatória")
    @Min(value = 1, message = "Duração deve ser maior que zero")
    private Integer duracaoMinutos;

    @Min(value = 0, message = "Tempo de tolerância não pode ser negativo")
    private Integer tempoToleranciaMin = 0;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoExecucaoRequest {

    @NotBlank(message = "ID do serviço é obrigatório")
    private String idServico;

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade = 1;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoProdutoRequest {

    @NotBlank(message = "ID do serviço é obrigatório")
    private String idServico;

    @NotBlank(message = "ID do produto é obrigatório")
    private String idProduto;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidade;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoComProdutosRequest {

    @Valid
    private ServicoRequest servico;

    private List<ProdutoQuantidade> produtos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoQuantidade {
        @NotBlank(message = "ID do produto é obrigatório")
        private String idProduto;

        @NotNull(message = "Quantidade é obrigatória")
        @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
        private BigDecimal quantidade;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoResponse {
    private String id;
    private String idSituacao;
    private Long codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private Integer duracaoMinutos;
    private Integer tempoToleranciaMin;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoExecucaoResponse {
    private String id;
    private String idServico;
    private Date dataExecucao;
    private Integer quantidade;
    private String observacao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServicoProdutoResponse {
    private String id;
    private String idServico;
    private String idProduto;
    private BigDecimal quantidade;
}
