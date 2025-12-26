package com.hubunity.core.domain.clientes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClienteController {

  private final ClienteService clienteService;

  @PostMapping("/cliente")
  public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest request) {
    ClienteResponse response = clienteService.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/clientes")
  public ResponseEntity<List<ClienteResponse>> listarTodos() {
    List<ClienteResponse> clientes = clienteService.listarTodos();
    return ResponseEntity.ok(clientes);
  }

  @GetMapping("/cliente/{id}")
  public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable String id) {
    ClienteResponse response = clienteService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/cliente/{id}")
  public ResponseEntity<Void> deletar(@PathVariable String id) {
    clienteService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
