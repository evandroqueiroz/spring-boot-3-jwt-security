package com.hubunity.core.domain.compras.cotacoes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoRequest {

  @NotBlank(message = "ID do fornecedor é obrigatório")
  private String idFornecedor;

  @NotBlank(message = "ID da situação é obrigatório")
  private String idSituacao;

  private LocalDate dataCotacao;

  private String observacao;

  @NotEmpty(message = "A cotação deve ter pelo menos um item")
  private List<CotacaoItemRequest> itens;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CotacaoItemRequest {

  private String idProduto;

  private String idServico;

  private BigDecimal quantidade;

  @NotNull(message = "Valor unitário é obrigatório")
  private BigDecimal valorUnitario;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CotacaoResponse {
  private String id;
  private String idEmpresa;
  private String idFornecedor;
  private String nomeFornecedor;
  private String idSituacao;
  private String nomeSituacao;
  private LocalDate dataCotacao;
  private String observacao;
  private List<CotacaoItemResponse> itens;
  private Date createdAt;
  private Date updatedAt;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CotacaoItemResponse {
  private String id;
  private String idProduto;
  private String nomeProduto;
  private String idServico;
  private String nomeServico;
  private BigDecimal quantidade;
  private BigDecimal valorUnitario;
  private BigDecimal subtotal;
}
