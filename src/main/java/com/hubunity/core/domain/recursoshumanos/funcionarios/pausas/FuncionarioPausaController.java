package com.hubunity.core.domain.recursoshumanos.funcionarios.pausas;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FuncionarioPausaController {

  private final FuncionarioPausaService pausaService;

  @PostMapping("/funcionario-pausa")
  public ResponseEntity<FuncionarioPausaResponse> criar(@RequestBody FuncionarioPausaRequest request) {
    FuncionarioPausaResponse response = pausaService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/funcionario-pausa/{id}")
  public ResponseEntity<FuncionarioPausaResponse> atualizar(@PathVariable String id,
      @RequestBody FuncionarioPausaRequest request) {
    FuncionarioPausaResponse response = pausaService.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/funcionario-pausa/{id}")
  public ResponseEntity<FuncionarioPausaResponse> buscarPorId(@PathVariable String id) {
    FuncionarioPausaResponse response = pausaService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/funcionario-pausas")
  public ResponseEntity<List<FuncionarioPausaResponse>> listarTodos() {
    List<FuncionarioPausaResponse> pausas = pausaService.listarTodos();
    return ResponseEntity.ok(pausas);
  }

  @GetMapping("/funcionario-pausa/tipo/{tipo}")
  public ResponseEntity<List<FuncionarioPausaResponse>> buscarPorTipo(@PathVariable String tipo) {
    List<FuncionarioPausaResponse> pausas = pausaService.buscarPorTipo(tipo);
    return ResponseEntity.ok(pausas);
  }

  @GetMapping("/funcionario-pausa/funcionario/{idFuncionario}")
  public ResponseEntity<List<FuncionarioPausaResponse>> buscarPorFuncionario(@PathVariable String idFuncionario) {
    List<FuncionarioPausaResponse> pausas = pausaService.buscarPorFuncionario(idFuncionario);
    return ResponseEntity.ok(pausas);
  }

  @GetMapping("/funcionario-pausa/ativo/{ativo}")
  public ResponseEntity<List<FuncionarioPausaResponse>> buscarPorAtivo(@PathVariable Boolean ativo) {
    List<FuncionarioPausaResponse> pausas = pausaService.buscarPorAtivo(ativo);
    return ResponseEntity.ok(pausas);
  }

  @DeleteMapping("/funcionario-pausa/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    pausaService.deletar(id);
    return ResponseEntity.noContent().build();
  }

}
