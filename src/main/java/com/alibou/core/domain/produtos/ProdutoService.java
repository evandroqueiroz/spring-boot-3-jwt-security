package com.alibou.core.domain.produtos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoEstoqueMovRepository estoqueMovRepository;

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setIdUnidadeMedida(request.getIdUnidadeMedida());
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoCompra(request.getPrecoCompra());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setEstoqueMinimo(request.getEstoqueMinimo());
        produto.setEstoqueAtual(request.getEstoqueAtual() != null ? request.getEstoqueAtual() : BigDecimal.ZERO);
        produto.setIdSituacao(request.getIdSituacao());

        Produto saved = produtoRepository.save(produto);
        return toResponse(saved);
    }

    @Transactional
    public ProdutoResponse atualizar(String id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setIdUnidadeMedida(request.getIdUnidadeMedida());
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoCompra(request.getPrecoCompra());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setEstoqueMinimo(request.getEstoqueMinimo());
        produto.setIdSituacao(request.getIdSituacao());

        Produto updated = produtoRepository.save(produto);
        return toResponse(updated);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(String id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return toResponse(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarProdutosComEstoqueBaixo() {
        return produtoRepository.findProdutosComEstoqueBaixo().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(String id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public void movimentarEstoque(ProdutoEstoqueMovRequest request) {
        Produto produto = produtoRepository.findById(request.getIdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProdutoEstoqueMov mov = new ProdutoEstoqueMov();
        mov.setIdProduto(request.getIdProduto());
        mov.setTipoMov(request.getTipoMov());
        mov.setQuantidade(request.getQuantidade());
        mov.setObservacao(request.getObservacao());

        estoqueMovRepository.save(mov);

        BigDecimal novoEstoque = produto.getEstoqueAtual();
        switch (request.getTipoMov()) {
            case "E": // Entrada
                novoEstoque = novoEstoque.add(request.getQuantidade());
                break;
            case "S": // Saída
                novoEstoque = novoEstoque.subtract(request.getQuantidade());
                if (novoEstoque.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Estoque insuficiente");
                }
                break;
            case "A": // Ajuste
                novoEstoque = request.getQuantidade();
                break;
        }

        produto.setEstoqueAtual(novoEstoque);
        produtoRepository.save(produto);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getIdSituacao(),
                produto.getIdUnidadeMedida(),
                produto.getCodigo(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPrecoCompra(),
                produto.getPrecoVenda(),
                produto.getEstoqueMinimo(),
                produto.getEstoqueAtual()
        );
    }
}