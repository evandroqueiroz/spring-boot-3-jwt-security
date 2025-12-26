package com.hubunity.core.domain.cotacoes;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.fornecedores.Fornecedor;
import com.hubunity.core.domain.fornecedores.FornecedorRepository;
import com.hubunity.core.domain.produtos.Produto;
import com.hubunity.core.domain.produtos.ProdutoRepository;
import com.hubunity.core.domain.servicos.Servico;
import com.hubunity.core.domain.servicos.ServicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CotacaoService {

  @PersistenceContext
  private EntityManager entityManager;

  private final CotacaoRepository cotacaoRepository;
  private final FornecedorRepository fornecedorRepository;
  private final ProdutoRepository produtoRepository;
  private final ServicoRepository servicoRepository;

  @Transactional
  public CotacaoResponse criar(CotacaoRequest request) {
    // Validar fornecedor
    Fornecedor fornecedor = fornecedorRepository.findById(request.getIdFornecedor())
        .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

    // Validar situação
    Situacao situacao = entityManager.getReference(Situacao.class, request.getIdSituacao());

    // Criar cotação
    Cotacao cotacao = new Cotacao();
    cotacao.setEmpresa(getEmpresaFromTenant());
    cotacao.setFornecedor(fornecedor);
    cotacao.setSituacao(situacao);
    cotacao.setDataCotacao(request.getDataCotacao());
    cotacao.setObservacao(request.getObservacao());

    // Adicionar itens
    for (CotacaoItemRequest itemRequest : request.getItens()) {
      CotacaoItem item = new CotacaoItem();
      item.setCotacao(cotacao);

      // Validar que pelo menos produto ou serviço foi informado
      if (itemRequest.getIdProduto() == null && itemRequest.getIdServico() == null) {
        throw new IllegalArgumentException("Item deve ter produto ou serviço");
      }

      if (itemRequest.getIdProduto() != null) {
        Produto produto = produtoRepository.findById(itemRequest.getIdProduto())
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemRequest.getIdProduto()));
        item.setProduto(produto);
      }

      if (itemRequest.getIdServico() != null) {
        Servico servico = servicoRepository.findById(itemRequest.getIdServico())
            .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + itemRequest.getIdServico()));
        item.setServico(servico);
      }

      item.setQuantidade(itemRequest.getQuantidade());
      item.setValorUnitario(itemRequest.getValorUnitario());

      cotacao.getItens().add(item);
    }

    Cotacao saved = cotacaoRepository.save(cotacao);
    return toResponse(saved);
  }

  @Transactional
  public CotacaoResponse atualizar(String id, CotacaoRequest request) {
    String idEmpresa = TenantContext.getCurrentTenant();
    Cotacao cotacao = cotacaoRepository.findByIdAndEmpresaId(id, idEmpresa)
        .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));

    // Validar fornecedor
    Fornecedor fornecedor = fornecedorRepository.findById(request.getIdFornecedor())
        .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

    // Validar situação
    Situacao situacao = entityManager.getReference(Situacao.class, request.getIdSituacao());

    // Atualizar cotação
    cotacao.setFornecedor(fornecedor);
    cotacao.setSituacao(situacao);
    cotacao.setDataCotacao(request.getDataCotacao());
    cotacao.setObservacao(request.getObservacao());

    // Remover itens antigos
    cotacao.getItens().clear();

    // Adicionar novos itens
    for (CotacaoItemRequest itemRequest : request.getItens()) {
      CotacaoItem item = new CotacaoItem();
      item.setCotacao(cotacao);

      // Validar que pelo menos produto ou serviço foi informado
      if (itemRequest.getIdProduto() == null && itemRequest.getIdServico() == null) {
        throw new IllegalArgumentException("Item deve ter produto ou serviço");
      }

      if (itemRequest.getIdProduto() != null) {
        Produto produto = produtoRepository.findById(itemRequest.getIdProduto())
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemRequest.getIdProduto()));
        item.setProduto(produto);
      }

      if (itemRequest.getIdServico() != null) {
        Servico servico = servicoRepository.findById(itemRequest.getIdServico())
            .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + itemRequest.getIdServico()));
        item.setServico(servico);
      }

      item.setQuantidade(itemRequest.getQuantidade());
      item.setValorUnitario(itemRequest.getValorUnitario());

      cotacao.getItens().add(item);
    }

    Cotacao updated = cotacaoRepository.save(cotacao);
    return toResponse(updated);
  }

  @Transactional(readOnly = true)
  public List<CotacaoResponse> listarTodos() {
    String idEmpresa = TenantContext.getCurrentTenant();
    return cotacaoRepository.findAllByEmpresaId(idEmpresa).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CotacaoResponse buscarPorId(String id) {
    String idEmpresa = TenantContext.getCurrentTenant();
    Cotacao cotacao = cotacaoRepository.findByIdAndEmpresaId(id, idEmpresa)
        .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));
    return toResponse(cotacao);
  }

  @Transactional(readOnly = true)
  public List<CotacaoResponse> buscarPorFornecedor(String idFornecedor) {
    String idEmpresa = TenantContext.getCurrentTenant();
    return cotacaoRepository.findByEmpresaIdAndFornecedorId(idEmpresa, idFornecedor).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<CotacaoResponse> buscarPorSituacao(String idSituacao) {
    String idEmpresa = TenantContext.getCurrentTenant();
    return cotacaoRepository.findByEmpresaIdAndSituacaoId(idEmpresa, idSituacao).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deletar(String id) {
    String idEmpresa = TenantContext.getCurrentTenant();
    Cotacao cotacao = cotacaoRepository.findByIdAndEmpresaId(id, idEmpresa)
        .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));
    cotacaoRepository.delete(cotacao);
  }

  private CotacaoResponse toResponse(Cotacao cotacao) {
    List<CotacaoItemResponse> itensResponse = cotacao.getItens().stream()
        .map(this::toItemResponse)
        .collect(Collectors.toList());

    return new CotacaoResponse(
        cotacao.getId(),
        cotacao.getEmpresa() != null ? cotacao.getEmpresa().getId() : null,
        cotacao.getFornecedor().getId(),
        cotacao.getFornecedor().getPessoa() != null ? cotacao.getFornecedor().getPessoa().getNomeCompleto() : null,
        cotacao.getSituacao().getId(),
        cotacao.getSituacao().getNome(),
        cotacao.getDataCotacao(),
        cotacao.getObservacao(),
        itensResponse,
        cotacao.getCreatedAt(),
        cotacao.getUpdatedAt());
  }

  private CotacaoItemResponse toItemResponse(CotacaoItem item) {
    BigDecimal subtotal = BigDecimal.ZERO;
    if (item.getQuantidade() != null && item.getValorUnitario() != null) {
      subtotal = item.getQuantidade().multiply(item.getValorUnitario());
    }

    return CotacaoItemResponse.builder()
        .id(item.getId())
        .idProduto(item.getProduto() != null ? item.getProduto().getId() : null)
        .nomeProduto(item.getProduto() != null ? item.getProduto().getNome() : null)
        .idServico(item.getServico() != null ? item.getServico().getId() : null)
        .nomeServico(item.getServico() != null ? item.getServico().getNome() : null)
        .quantidade(item.getQuantidade())
        .valorUnitario(item.getValorUnitario())
        .subtotal(subtotal)
        .build();
  }

  private Empresa getEmpresaFromTenant() {
    String idEmpresa = TenantContext.getCurrentTenant();

    if (idEmpresa == null || idEmpresa.isBlank()) {
      throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
    }

    return entityManager.getReference(Empresa.class, idEmpresa);
  }
}
