package com.hubunity.core.domain.localidade.tipologradouro;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-logradouro")
@RequiredArgsConstructor
public class TipoLogradouroController {

  private final TipoLogradouroService service;

  @GetMapping
  public List<TipoLogradouro> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TipoLogradouro> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<TipoLogradouro> salvar(@RequestBody TipoLogradouro tipoLogradouro) {
    return ResponseEntity.ok(service.salvar(tipoLogradouro));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
