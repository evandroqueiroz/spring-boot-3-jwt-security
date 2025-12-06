package com.alibou.core.domain.al_servicos;

import com.alibou.core.domain.situacao.Situacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository repository;

    public void salvar(ServicoRequest request) {
        var servico = Servico.builder()
                .id(request.getId())
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .duracao_minutos(request.getDuracao_minutos())
                .preco(request.getPreco())
                .descricao(request.getDescricao())
                .situacao(Situacao.builder()
                        .id(String.valueOf(request.getId_situacao()))
                        .build()
                )
                .build();
        repository.save(servico);
    }

    public List<Servico> buscarTodos() {
        return repository.findAll();
    }

    public Servico buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(("Serviço não encontrado")));
    }

    public Servico atualizar(String id, ServicoRequest request) {
        Servico servicoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoExistente.setNome(request.getNome());
        servicoExistente.setCategoria(request.getCategoria());
        servicoExistente.setDuracao_minutos(request.getDuracao_minutos());
        servicoExistente.setPreco(request.getPreco());
        servicoExistente.setDescricao(request.getDescricao());
        servicoExistente.setSituacao(
                Situacao.builder()
                        .id(request.getId_situacao())
                        .build()
        );
        return repository.save(servicoExistente);
    }


    public void deletarPorId(String id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        repository.delete(servico);
    }


}
