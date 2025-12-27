package com.hubunity.core.domain.core.pessoas;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping("/pessoa")
    public ResponseEntity<PessoaResponse> criar(@RequestBody PessoaRequest request) {
        PessoaResponse response = pessoaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable String id, @RequestBody PessoaRequest request) {
        PessoaResponse response = pessoaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaResponse>> listarTodos() {
        List<PessoaResponse> pessoas = pessoaService.listarTodos();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<PessoaResponse> buscarPorId(@PathVariable String id) {
        PessoaResponse response = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
