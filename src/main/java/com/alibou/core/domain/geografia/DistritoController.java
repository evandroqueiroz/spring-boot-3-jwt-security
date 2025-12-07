package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distritos")
@RequiredArgsConstructor
public class DistritoController {

  private final DistritoService service;

  @GetMapping
  public List<Distrito> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Distrito> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Distrito> salvar(@RequestBody Distrito distrito) {
    return ResponseEntity.ok(service.salvar(distrito));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
