package com.hubunity.core.domain.core.empresa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping("/empresa")
    public ResponseEntity<EmpresaResponseDTO> criar(@RequestBody EmpresaRequest request) {
        EmpresaResponseDTO response = empresaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/empresa/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable String id, @RequestBody EmpresaRequest request) {
        EmpresaResponseDTO response = empresaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/empresas")
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodos() {
        List<EmpresaResponseDTO> empresas = empresaService.listarTodos();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable String id) {
        EmpresaResponseDTO response = empresaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
