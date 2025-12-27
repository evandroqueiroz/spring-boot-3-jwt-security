package com.hubunity.core.domain.localidade.estado;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @PostMapping("/estado")
    public ResponseEntity<EstadoResponse> criar(@RequestBody EstadoRequest request) {
        EstadoResponse response = estadoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<EstadoResponse> atualizar(@PathVariable String id, @RequestBody EstadoRequest request) {
        EstadoResponse response = estadoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<EstadoResponse>> listarTodos() {
        List<EstadoResponse> estados = estadoService.listarTodos();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/estado/{id}")
    public ResponseEntity<EstadoResponse> buscarPorId(@PathVariable String id) {
        EstadoResponse response = estadoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/estado/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        estadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
