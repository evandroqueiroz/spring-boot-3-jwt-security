package com.alibou.core.domain.produtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    @NotBlank(message = "ID da unidade de medida é obrigatório")
    private String idUnidadeMedida;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 255, message = "Categoria deve ter no máximo 255 caracteres")
    private String categoria;

    @PositiveOrZero(message = "Preço de compra deve ser positivo")
    private Double precoCompra;

    @PositiveOrZero(message = "Preço de venda deve ser positivo")
    private Double precoVenda;

    @DecimalMin(value = "0.0", message = "Estoque mínimo deve ser positivo")
    private BigDecimal estoqueMinimo;

    @DecimalMin(value = "0.0", message = "Estoque atual não pode ser negativo")
    private BigDecimal estoqueAtual;

    private String idSituacao = "A";
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProdutoEstoqueMovRequest {

    @NotBlank(message = "ID do produto é obrigatório")
    private String idProduto;

    @NotBlank(message = "Tipo de movimentação é obrigatório")
    @Pattern(regexp = "[ESA]", message = "Tipo deve ser E (Entrada), S (Saída) ou A (Ajuste)")
    private String tipoMov;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidade;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProdutoResponse {
    private String id;
    private String idSituacao;
    private String idUnidadeMedida;
    private Long codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private Double precoCompra;
    private Double precoVenda;
    private BigDecimal estoqueMinimo;
    private BigDecimal estoqueAtual;
}
