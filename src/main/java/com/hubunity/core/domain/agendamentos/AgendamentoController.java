package com.hubunity.core.domain.agendamentos;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

  @Autowired
  private AgendamentoService service;

  @PostMapping
  public ResponseEntity<AgendamentoResponse> create(@RequestBody @Valid AgendamentoRequest request) {
    AgendamentoResponse novoAgendamento = service.create(request);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(novoAgendamento.getId()).toUri();
    return ResponseEntity.created(uri).body(novoAgendamento);
  }

  @GetMapping
  public ResponseEntity<List<AgendamentoResponse>> listAll() {
    return ResponseEntity.ok(service.listAll());
  }
}
