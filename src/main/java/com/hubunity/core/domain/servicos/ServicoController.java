package com.hubunity.core.domain.servicos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping("/servico")
    public ResponseEntity<ServicoResponse> criar(@Valid @RequestBody ServicoRequest request) {
        ServicoResponse response = servicoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/servico/com-produtos")
    public ResponseEntity<ServicoResponse> criarComProdutos(
            @Valid @RequestBody ServicoComProdutosRequest request) {
        ServicoResponse response = servicoService.criarComProdutos(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/servico/{id}")
    public ResponseEntity<ServicoResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ServicoRequest request) {
        ServicoResponse response = servicoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/servico/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable String id) {
        ServicoResponse response = servicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<ServicoResponse>> listarTodos() {
        List<ServicoResponse> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/servico/categoria/{categoria}")
    public ResponseEntity<List<ServicoResponse>> buscarPorCategoria(@PathVariable String categoria) {
        List<ServicoResponse> servicos = servicoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/servico/promocoes")
    public ResponseEntity<List<ServicoResponse>> buscarPromocoes() {
        List<ServicoResponse> servicos = servicoService.buscarServicosComPromocao();
        return ResponseEntity.ok(servicos);
    }

    @DeleteMapping("/servico/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/servico/executar")
    public ResponseEntity<ServicoExecucaoResponse> executarServico(
            @Valid @RequestBody ServicoExecucaoRequest request) {
        ServicoExecucaoResponse response = servicoService.executarServico(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/servico/produtos/vincular")
    public ResponseEntity<ServicoProdutoResponse> vincularProduto(
            @Valid @RequestBody ServicoProdutoRequest request) {
        ServicoProdutoResponse response = servicoService.vincularProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/servico/{id}/produtos")
    public ResponseEntity<List<ServicoProdutoResponse>> listarProdutos(@PathVariable String id) {
        List<ServicoProdutoResponse> produtos = servicoService.listarProdutosPorServico(id);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/servico/produtos/{id}")
    public ResponseEntity<Void> desvincularProduto(@PathVariable String id) {
        servicoService.desvincularProduto(id);
        return ResponseEntity.noContent().build();
    }
}