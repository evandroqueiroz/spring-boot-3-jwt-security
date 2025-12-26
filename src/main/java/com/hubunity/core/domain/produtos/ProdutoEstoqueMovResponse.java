package com.hubunity.core.domain.produtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEstoqueMovResponse {
  private String id;
  private String idEmpresa;
  private String idProduto;
  private String nomeProduto;
  private String tipoMov; // E, S, A
  private BigDecimal quantidade;
  private Date dataMov;
  private String observacao;
}
