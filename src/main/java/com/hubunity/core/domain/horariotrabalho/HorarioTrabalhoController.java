package com.hubunity.core.domain.horariotrabalho;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/horarios-trabalho")
public class HorarioTrabalhoController {

  @Autowired
  private HorarioTrabalhoService service;

  @PostMapping
  public ResponseEntity<HorarioTrabalhoResponse> create(@RequestBody @Valid HorarioTrabalhoRequest request) {
    HorarioTrabalhoResponse novoHorario = service.create(request);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(novoHorario.getId()).toUri();
    return ResponseEntity.created(uri).body(novoHorario);
  }

  @GetMapping("/funcionario/{idFuncionario}")
  public ResponseEntity<List<HorarioTrabalhoResponse>> listByFuncionario(@PathVariable String idFuncionario) {
    return ResponseEntity.ok(service.listByFuncionario(idFuncionario));
  }
}
