package com.hubunity.core.domain.localidade.pais;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService paisService;

    @PostMapping("/pais")
    public ResponseEntity<PaisResponse> criar(@RequestBody PaisRequest request) {
        PaisResponse response = paisService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/pais/{id}")
    public ResponseEntity<PaisResponse> atualizar(@PathVariable String id, @RequestBody PaisRequest request) {
        PaisResponse response = paisService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paises")
    public ResponseEntity<List<PaisResponse>> listarTodos() {
        List<PaisResponse> paises = paisService.listarTodos();
        return ResponseEntity.ok(paises);
    }

    @GetMapping("/pais/{id}")
    public ResponseEntity<PaisResponse> buscarPorId(@PathVariable String id) {
        PaisResponse response = paisService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pais/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        paisService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
