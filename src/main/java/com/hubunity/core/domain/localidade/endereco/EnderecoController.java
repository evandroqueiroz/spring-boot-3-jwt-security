package com.hubunity.core.domain.localidade.endereco;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EnderecoController {

  private final EnderecoService enderecoService;

  @PostMapping("/endereco")
  public ResponseEntity<EnderecoResponseDTO> criar(@RequestBody EnderecoRequest request) {
    EnderecoResponseDTO response = enderecoService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/endereco/{id}")
  public ResponseEntity<EnderecoResponseDTO> atualizar(@PathVariable String id, @RequestBody EnderecoRequest request) {
    EnderecoResponseDTO response = enderecoService.atualizar(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/enderecos")
  public ResponseEntity<List<EnderecoResponseDTO>> listarTodos() {
    List<EnderecoResponseDTO> enderecos = enderecoService.listarTodos();
    return ResponseEntity.ok(enderecos);
  }

  @GetMapping("/endereco/{id}")
  public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable String id) {
    EnderecoResponseDTO response = enderecoService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/endereco/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    enderecoService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
