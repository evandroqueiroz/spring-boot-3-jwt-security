package com.hubunity.core.domain.localidade.municipio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/municipios")
@RequiredArgsConstructor
public class MunicipioController {

  private final MunicipioService service;

  @GetMapping
  public List<Municipio> buscarTodos() {
    return service.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Municipio> buscarPorId(@PathVariable String id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Municipio> salvar(@RequestBody Municipio municipio) {
    return ResponseEntity.ok(service.salvar(municipio));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
