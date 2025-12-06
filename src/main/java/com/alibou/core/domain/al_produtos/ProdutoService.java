package com.alibou.core.domain.al_produtos;

import com.alibou.core.domain.situacao.Situacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public void salvar(ProdutoRequest request) {
        var produto = Produto.builder()
                .id(request.getId())
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .precoCompra(request.getPrecoCompra())
                .precoVenda(request.getPrecoVenda())
                .descricao(request.getDescricao())
                .situacao(Situacao.builder()
                        .id(String.valueOf(request.getId_situacao()))
                        .build()
                )
                .build();
        repository.save(produto);
    }

    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(("Produto não encontrado")));
    }

    public Produto atualizar(String id, ProdutoRequest request) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(request.getNome());
        produtoExistente.setCategoria(request.getCategoria());
        produtoExistente.setPrecoCompra(request.getPrecoCompra());
        produtoExistente.setPrecoVenda(request.getPrecoVenda());
        produtoExistente.setDescricao(request.getDescricao());
        produtoExistente.setSituacao(
                Situacao.builder()
                        .id(request.getId_situacao())
                        .build()
        );
        return repository.save(produtoExistente);
    }

    public void deletarPorId(String id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        repository.delete(produto);
    }

}
