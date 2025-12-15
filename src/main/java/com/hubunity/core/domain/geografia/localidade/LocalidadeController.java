package com.hubunity.core.domain.geografia.localidade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidades")
@RequiredArgsConstructor
public class LocalidadeController {

  private final LocalidadeService service;

  @GetMapping
  public List<Localidade> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Localidade> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Localidade> salvar(@RequestBody Localidade localidade) {
    return ResponseEntity.ok(service.salvar(localidade));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
