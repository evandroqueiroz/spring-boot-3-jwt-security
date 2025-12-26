package com.hubunity.core.domain.cotacoes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CotacaoController {

  private final CotacaoService service;

  @PostMapping("/cotacao")
  public ResponseEntity<CotacaoResponse> criar(@Valid @RequestBody CotacaoRequest request) {
    CotacaoResponse response = service.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/cotacao/{id}")
  public ResponseEntity<CotacaoResponse> atualizar(
      @PathVariable String id,
      @Valid @RequestBody CotacaoRequest request) {
    CotacaoResponse response = service.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/cotacoes")
  public ResponseEntity<List<CotacaoResponse>> listarTodos() {
    return ResponseEntity.ok(service.listarTodos());
  }

  @GetMapping("/cotacao/{id}")
  public ResponseEntity<CotacaoResponse> buscarPorId(@PathVariable String id) {
    CotacaoResponse response = service.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/cotacoes/fornecedor/{idFornecedor}")
  public ResponseEntity<List<CotacaoResponse>> buscarPorFornecedor(@PathVariable String idFornecedor) {
    return ResponseEntity.ok(service.buscarPorFornecedor(idFornecedor));
  }

  @GetMapping("/cotacoes/situacao/{idSituacao}")
  public ResponseEntity<List<CotacaoResponse>> buscarPorSituacao(@PathVariable String idSituacao) {
    return ResponseEntity.ok(service.buscarPorSituacao(idSituacao));
  }

  @DeleteMapping("/cotacao/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
