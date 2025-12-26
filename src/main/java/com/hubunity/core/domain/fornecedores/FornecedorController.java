package com.hubunity.core.domain.fornecedores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FornecedorController {

  private final FornecedorService fornecedorService;

  @PostMapping("/fornecedor")
  public ResponseEntity<FornecedorResponse> criar(@RequestBody FornecedorRequest request) {
    FornecedorResponse response = fornecedorService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/fornecedores")
  public ResponseEntity<List<FornecedorResponse>> listarTodos() {
    List<FornecedorResponse> fornecedores = fornecedorService.listarTodos();
    return ResponseEntity.ok(fornecedores);
  }

  @GetMapping("/fornecedor/{id}")
  public ResponseEntity<FornecedorResponse> buscarPorId(@PathVariable String id) {
    FornecedorResponse response = fornecedorService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/fornecedor/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    fornecedorService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
