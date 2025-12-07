package com.alibou.core.domain.agendamentos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

  private final AgendamentoService service;

  @PostMapping
  public ResponseEntity<?> save(
      @RequestBody AgendamentoRequest request) {
    service.save(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<List<Agendamento>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Agendamento> findById(@PathVariable String id) {
    Agendamento agendamento = service.findById(id);
    if (agendamento == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(agendamento);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.accepted().build();
  }
}
