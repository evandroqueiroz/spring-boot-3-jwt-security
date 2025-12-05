package com.alibou.core.domain.situacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SituacaoService {

    private final SituacaoRepository repository;

    public Situacao salvar(Situacao situacao) {
        return repository.save(situacao);
    }

    public Situacao buscarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Situação não encontrada"));
    }

    public List<Situacao> listarTodas() {
        return repository.findAll();
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }
}
