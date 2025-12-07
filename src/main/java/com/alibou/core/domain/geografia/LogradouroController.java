package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logradouros")
@RequiredArgsConstructor
public class LogradouroController {

  private final LogradouroService service;

  @GetMapping
  public List<Logradouro> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Logradouro> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Logradouro> salvar(@RequestBody Logradouro logradouro) {
    return ResponseEntity.ok(service.salvar(logradouro));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
