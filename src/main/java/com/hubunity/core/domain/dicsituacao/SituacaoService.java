package com.hubunity.core.domain.dicsituacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SituacaoService {

    private final SituacaoRepository repository;

    public void salvar(SituacaoRequest request) {
        var situacao = Situacao.builder()
                .id(request.getId())
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .build();
        repository.save(situacao);
    }

    public List<Situacao> buscarTodos() {
        return repository.findAll();
    }

    public Situacao buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(("Situação não encontrada")));
    }

    public Situacao atualizar(String id, SituacaoRequest request) {
        Situacao situacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Situação não encontrada"));

        situacaoExistente.setNome(request.getNome());
        situacaoExistente.setDescricao(request.getDescricao());

        return repository.save(situacaoExistente);
    }

    public void deletarPorId(String id) {
        Situacao situacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Situação não encontrada"));

        repository.delete(situacao);
    }
}
