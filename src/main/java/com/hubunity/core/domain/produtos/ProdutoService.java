package com.hubunity.core.domain.produtos;

import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.unidademedida.UnidadeMedida;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProdutoRepository produtoRepository;
    private final ProdutoEstoqueMovRepository estoqueMovRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public ProdutoResponse criar(ProdutoEstoqueMovRequest.ProdutoRequest request) {
        Produto produto = new Produto();

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoCompra(request.getPrecoCompra());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setEstoqueMinimo(request.getEstoqueMinimo());
        produto.setEstoqueAtual(request.getEstoqueAtual() != null ? request.getEstoqueAtual() : BigDecimal.ZERO);

        produto.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        produto.setUnidadeMedida(entityManager.getReference(UnidadeMedida.class, request.getIdUnidadeMedida()));
        produto.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));

        Produto saved = produtoRepository.save(produto);
        return toResponse(saved);
    }

    @Transactional
    public void movimentarEstoque(ProdutoEstoqueMovRequest request) {
        Produto produto = produtoRepository.findById(request.getIdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProdutoEstoqueMov mov = new ProdutoEstoqueMov();
        mov.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        mov.setProduto(entityManager.getReference(Produto.class, request.getIdProduto()));

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

    // -------------------- PUT -------------------- //

    @Transactional
    public ProdutoResponse atualizar(String id, ProdutoEstoqueMovRequest.ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoCompra(request.getPrecoCompra());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setEstoqueMinimo(request.getEstoqueMinimo());

        produto.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        produto.setUnidadeMedida(entityManager.getReference(UnidadeMedida.class, request.getIdUnidadeMedida()));
        produto.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));

        Produto updated = produtoRepository.save(produto);
        return toResponse(updated);
    }


    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(String id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return toResponse(produto);
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

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getEmpresa() != null ? produto.getEmpresa().getId() : null,
                produto.getSituacao() != null ? produto.getSituacao().getId() : null,
                produto.getUnidadeMedida() != null ? produto.getUnidadeMedida().getId() : null,
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