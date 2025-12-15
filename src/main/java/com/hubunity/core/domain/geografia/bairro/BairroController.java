package com.hubunity.core.domain.geografia.bairro;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairros")
@RequiredArgsConstructor
public class BairroController {

  private final BairroService service;

  @GetMapping
  public List<Bairro> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Bairro> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Bairro> salvar(@RequestBody Bairro bairro) {
    return ResponseEntity.ok(service.salvar(bairro));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
