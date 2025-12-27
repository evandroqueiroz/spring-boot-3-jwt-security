package com.hubunity.core.domain.core.unidademedida;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UnidadeMedidaController {

    private final UnidadeMedidaService unidadeMedidaService;

    @PostMapping("/unidade-medida")
    public ResponseEntity<UnidadeMedidaResponse> criar(@Valid @RequestBody UnidadeMedidaRequest request) {
        UnidadeMedidaResponse response = unidadeMedidaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/unidade-medida/{id}")
    public ResponseEntity<UnidadeMedidaResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody UnidadeMedidaRequest request) {
        UnidadeMedidaResponse response = unidadeMedidaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unidade-medidas")
    public ResponseEntity<List<UnidadeMedidaResponse>> buscarTodos() {
        List<UnidadeMedidaResponse> produtos = unidadeMedidaService.buscarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/unidade-medida/{id}")
    public ResponseEntity<UnidadeMedidaResponse> buscarPorId(@PathVariable String id) {
        UnidadeMedidaResponse response = unidadeMedidaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unidade-medida/codigo/{codigo}")
    public ResponseEntity<List<UnidadeMedidaResponse>> buscarPorCodigo(@PathVariable String codigo) {
        List<UnidadeMedidaResponse> unidadeMedidas = unidadeMedidaService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(unidadeMedidas);
    }

    @GetMapping("/unidade-medida/nome/{nome}")
    public ResponseEntity<List<UnidadeMedidaResponse>> buscarPorNome(@PathVariable String nome) {
        List<UnidadeMedidaResponse> unidadeMedidas = unidadeMedidaService.buscarPorNome(nome);
        return ResponseEntity.ok(unidadeMedidas);
    }

    @DeleteMapping("/unidade-medida/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        unidadeMedidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
