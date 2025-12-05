package com.alibou.core.domain.al_servicos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService service;

    @PostMapping("/servico")
    public ResponseEntity<?> save(@RequestBody ServicoRequest request) {
        service.salvar(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Servico>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/servico/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/servico/{id}")
    public ResponseEntity<Servico> atualizar(
            @PathVariable String id,
            @RequestBody ServicoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }


    @DeleteMapping("/servico/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable String id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
