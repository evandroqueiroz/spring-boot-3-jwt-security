package com.alibou.core.domain.filial;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filiais")
@RequiredArgsConstructor
public class FilialController {

    private final FilialService service;

    @GetMapping
    public List<Filial> buscarTodasAsFiliais() {
        return service.buscarTodasAsFiliais();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filial> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Filial> salvar(@RequestBody Filial filial) {
        return ResponseEntity.ok(service.salvar(filial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Filial> deletarPorId(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
