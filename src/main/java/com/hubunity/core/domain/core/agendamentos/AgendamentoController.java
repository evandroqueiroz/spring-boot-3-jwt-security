package com.hubunity.core.domain.core.agendamentos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AgendamentoController {

  private final AgendamentoService service;

  @PostMapping("/agendamento")
  public ResponseEntity<AgendamentoResponse> create(@RequestBody AgendamentoRequest request) {
    AgendamentoResponse response = service.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/agendamento/{id}")
  public ResponseEntity<AgendamentoResponse> atualizar(@PathVariable String id,
      @RequestBody AgendamentoRequest request) {
    AgendamentoResponse response = service.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/agendamentos")
  public ResponseEntity<List<AgendamentoResponse>> listAll() {
    return ResponseEntity.ok(service.listAll());
  }

  @GetMapping("/agendamento/{id}")
  public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable String id) {
    AgendamentoResponse response = service.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/agendamento/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
