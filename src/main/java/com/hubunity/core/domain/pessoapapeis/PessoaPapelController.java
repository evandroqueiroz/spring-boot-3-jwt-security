package com.hubunity.core.domain.pessoapapeis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PessoaPapelController {

    private final PessoaPapelService pessoaPapelService;

    @PostMapping("/pessoa-papel")
    public ResponseEntity<PessoaPapelResponse> criar(@RequestBody PessoaPapelRequest request) {
        PessoaPapelResponse response = pessoaPapelService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/pessoa-papel/{id}")
    public ResponseEntity<PessoaPapelResponse> atualizar(@PathVariable String id, @RequestBody PessoaPapelRequest request) {
        PessoaPapelResponse response = pessoaPapelService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pessoa-papel/{id}")
    public ResponseEntity<PessoaPapelResponse> buscarPorId(@PathVariable String id) {
        PessoaPapelResponse response = pessoaPapelService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pessoa-papeis")
    public ResponseEntity<List<PessoaPapelResponse>> listarTodos() {
        List<PessoaPapelResponse> response = pessoaPapelService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pessoa-papel/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        pessoaPapelService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
