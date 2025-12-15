package com.hubunity.core.domain.dicpapeis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PapelController {

    private final PapelService papelService;

    @PostMapping("/papel")
    public ResponseEntity<PapelResponse> criar(@RequestBody PapelRequest request) {
        PapelResponse response = papelService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/papel/{id}")
    public ResponseEntity<PapelResponse> atualizar(@PathVariable String id, @RequestBody PapelRequest request) {
        PapelResponse response = papelService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/papel/{id}")
    public ResponseEntity<PapelResponse> buscarPorId(@PathVariable String id) {
        PapelResponse response = papelService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/papeis")
    public ResponseEntity<List<PapelResponse>> listarTodos() {
        List<PapelResponse> papeis = papelService.listarTodos();
        return ResponseEntity.ok(papeis);
    }

    @DeleteMapping("/papel/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        papelService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
