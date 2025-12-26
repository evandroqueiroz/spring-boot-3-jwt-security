package com.hubunity.core.domain.produtos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/produto")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoEstoqueMovRequest.ProdutoRequest request) {
        ProdutoResponse response = produtoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/produto/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ProdutoEstoqueMovRequest.ProdutoRequest request) {
        ProdutoResponse response = produtoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoResponse>> listarTodos() {
        List<ProdutoResponse> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable String id) {
        ProdutoResponse response = produtoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produto/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponse>> buscarPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponse> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/produto/estoque-baixo")
    public ResponseEntity<List<ProdutoResponse>> buscarEstoqueBaixo() {
        List<ProdutoResponse> produtos = produtoService.buscarProdutosComEstoqueBaixo();
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/produto/movimentar-estoque")
    public ResponseEntity<Void> movimentarEstoque(@Valid @RequestBody ProdutoEstoqueMovRequest request) {
        produtoService.movimentarEstoque(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/produto/movimentacoes")
    public ResponseEntity<List<ProdutoEstoqueMovResponse>> listarMovimentacoes() {
        List<ProdutoEstoqueMovResponse> movimentacoes = produtoService.listarMovimentacoes();
        return ResponseEntity.ok(movimentacoes);
    }

}