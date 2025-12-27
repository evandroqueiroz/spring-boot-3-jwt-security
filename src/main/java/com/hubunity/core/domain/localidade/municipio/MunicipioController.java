package com.hubunity.core.domain.localidade.municipio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MunicipioController {

  private final MunicipioService municipioService;

  @PostMapping("/municipio")
  public ResponseEntity<MunicipioResponse> criar(@RequestBody MunicipioRequest request) {
    MunicipioResponse response = municipioService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/municipio/{id}")
  public ResponseEntity<MunicipioResponse> atualizar(@PathVariable String id, @RequestBody MunicipioRequest request) {
    MunicipioResponse response = municipioService.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/municipios")
  public ResponseEntity<List<MunicipioResponse>> listarTodos() {
    List<MunicipioResponse> municipios = municipioService.listarTodos();
    return ResponseEntity.ok(municipios);
  }

  @GetMapping("/municipio/{id}")
  public ResponseEntity<MunicipioResponse> buscarPorId(@PathVariable String id) {
    MunicipioResponse response = municipioService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/municipio/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    municipioService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
