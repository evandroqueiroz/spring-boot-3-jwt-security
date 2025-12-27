package com.hubunity.core.domain.core.horariotrabalho;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HorarioTrabalhoController {

  private final HorarioTrabalhoService horarioService;

  @PostMapping("/horario-trabalho")
  public ResponseEntity<HorarioTrabalhoResponse> criar(@RequestBody HorarioTrabalhoRequest request) {
    HorarioTrabalhoResponse response = horarioService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/horario-trabalho/{id}")
  public ResponseEntity<HorarioTrabalhoResponse> atualizar(@PathVariable String id,
      @RequestBody HorarioTrabalhoRequest request) {
    HorarioTrabalhoResponse response = horarioService.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/horario-trabalho/{id}")
  public ResponseEntity<HorarioTrabalhoResponse> buscarPorId(@PathVariable String id) {
    HorarioTrabalhoResponse response = horarioService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/horarios-trabalho")
  public ResponseEntity<List<HorarioTrabalhoResponse>> listarTodos() {
    List<HorarioTrabalhoResponse> horarios = horarioService.listarTodos();
    return ResponseEntity.ok(horarios);
  }

  @GetMapping("/horario-trabalho/funcionario/{idFuncionario}")
  public ResponseEntity<List<HorarioTrabalhoResponse>> buscarPorFuncionario(@PathVariable String idFuncionario) {
    List<HorarioTrabalhoResponse> horarios = horarioService.buscarPorFuncionario(idFuncionario);
    return ResponseEntity.ok(horarios);
  }

  @GetMapping("/horario-trabalho/dia-semana/{diaSemana}")
  public ResponseEntity<List<HorarioTrabalhoResponse>> buscarPorDiaSemana(@PathVariable Integer diaSemana) {
    List<HorarioTrabalhoResponse> horarios = horarioService.buscarPorDiaSemana(diaSemana);
    return ResponseEntity.ok(horarios);
  }

  @GetMapping("/horario-trabalho/ativo/{ativo}")
  public ResponseEntity<List<HorarioTrabalhoResponse>> buscarPorAtivo(@PathVariable Boolean ativo) {
    List<HorarioTrabalhoResponse> horarios = horarioService.buscarPorAtivo(ativo);
    return ResponseEntity.ok(horarios);
  }

  @DeleteMapping("/horario-trabalho/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    horarioService.deletar(id);
    return ResponseEntity.noContent().build();
  }

}
