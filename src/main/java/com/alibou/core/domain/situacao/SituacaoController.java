package com.alibou.core.domain.situacao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SituacaoController {

    private final SituacaoService service;

    @PostMapping("/situacao")
    public ResponseEntity<?> save(@RequestBody SituacaoRequest request) {
        service.salvar(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/situacoes")
    public ResponseEntity<List<Situacao>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/situacao/{id}")
    public ResponseEntity<Situacao> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/situacao/{id}")
    public ResponseEntity<Situacao> atualizar(
            @PathVariable String id,
            @RequestBody SituacaoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }


    @DeleteMapping("/situacao/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable String id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
