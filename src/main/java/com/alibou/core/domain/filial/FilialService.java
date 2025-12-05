package com.alibou.core.domain.filial;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilialService {

    private final FilialRepository repository;

    public Filial salvar(Filial filial) {
        return repository.save(filial);
    }

    public Filial atualizar(Filial filial) {
        return repository.save(filial);
    }

    public List<Filial> buscarTodasAsFiliais() {
        return repository.findAll();
    }

    public Filial buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Filial não encontrada"));
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }

}
