package com.hubunity.core.domain.funcionarios;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping("/funcionario")
    public ResponseEntity<FuncionarioResponse> criar(@RequestBody FuncionarioRequest request) {
        FuncionarioResponse response = funcionarioService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/funcionario/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable String id, @RequestBody FuncionarioRequest request) {
        FuncionarioResponse response = funcionarioService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable String id) {
        FuncionarioResponse response = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioResponse>> listarTodos() {
        List<FuncionarioResponse> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/funcionario/funcao/{funcao}")
    public ResponseEntity<List<FuncionarioResponse>> buscarPorCategoria(@PathVariable String funcao) {
        List<FuncionarioResponse> funcionarios = funcionarioService.buscarPorFuncao(funcao);
        return ResponseEntity.ok(funcionarios);
    }

    @DeleteMapping("/funcionario/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
