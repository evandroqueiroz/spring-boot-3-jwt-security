package com.alibou.core.domain.al_produtos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping("/produto")
    public ResponseEntity<?> save(@RequestBody ProdutoRequest request) {
        service.salvar(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/produto/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable String id,
            @RequestBody ProdutoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }


    @DeleteMapping("/produto/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable String id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
