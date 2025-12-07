package com.alibou.core.domain.pessoas;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pessoas")
@RequiredArgsConstructor
public class PessoaController {

  private final PessoaService service;

  @PostMapping
  public ResponseEntity<?> save(
      @RequestBody PessoaRequest request) {
    service.save(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<List<Pessoa>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pessoa> findById(@PathVariable String id) {
    Pessoa pessoa = service.findById(id);
    if (pessoa == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(pessoa);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.accepted().build();
  }
}
