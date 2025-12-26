package com.hubunity.core.domain.funcionarios.folgas;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FuncionarioFolgaController {

  private final FuncionarioFolgaService folgaService;

  @PostMapping("/funcionario-folga")
  public ResponseEntity<FuncionarioFolgaResponse> criar(@RequestBody FuncionarioFolgaRequest request) {
    FuncionarioFolgaResponse response = folgaService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/funcionario-folga/{id}")
  public ResponseEntity<FuncionarioFolgaResponse> atualizar(@PathVariable String id,
      @RequestBody FuncionarioFolgaRequest request) {
    FuncionarioFolgaResponse response = folgaService.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/funcionario-folga/{id}")
  public ResponseEntity<FuncionarioFolgaResponse> buscarPorId(@PathVariable String id) {
    FuncionarioFolgaResponse response = folgaService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/funcionario-folgas")
  public ResponseEntity<List<FuncionarioFolgaResponse>> listarTodos() {
    List<FuncionarioFolgaResponse> folgas = folgaService.listarTodos();
    return ResponseEntity.ok(folgas);
  }

  @GetMapping("/funcionario-folga/tipo/{tipo}")
  public ResponseEntity<List<FuncionarioFolgaResponse>> buscarPorTipo(@PathVariable String tipo) {
    List<FuncionarioFolgaResponse> folgas = folgaService.buscarPorTipo(tipo);
    return ResponseEntity.ok(folgas);
  }

  @GetMapping("/funcionario-folga/funcionario/{idFuncionario}")
  public ResponseEntity<List<FuncionarioFolgaResponse>> buscarPorFuncionario(@PathVariable String idFuncionario) {
    List<FuncionarioFolgaResponse> folgas = folgaService.buscarPorFuncionario(idFuncionario);
    return ResponseEntity.ok(folgas);
  }

  @GetMapping("/funcionario-folga/aprovado/{aprovado}")
  public ResponseEntity<List<FuncionarioFolgaResponse>> buscarPorAprovado(@PathVariable Boolean aprovado) {
    List<FuncionarioFolgaResponse> folgas = folgaService.buscarPorAprovado(aprovado);
    return ResponseEntity.ok(folgas);
  }

  @DeleteMapping("/funcionario-folga/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    folgaService.deletar(id);
    return ResponseEntity.noContent().build();
  }

}
