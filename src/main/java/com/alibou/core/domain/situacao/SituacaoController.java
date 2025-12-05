package com.alibou.core.domain.situacao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/situacoes")
@RequiredArgsConstructor
public class SituacaoController {

    private final SituacaoService service;

    @GetMapping
    public ResponseEntity<List<Situacao>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Situacao> buscar(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Situacao> salvar(@RequestBody Situacao situacao) {
        return ResponseEntity.ok(service.salvar(situacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
