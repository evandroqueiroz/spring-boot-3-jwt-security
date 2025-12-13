package com.alibou.core.domain.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {

    Optional<Produto> findByCodigo(Long codigo);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByIdSituacao(String idSituacao);

    @Query("SELECT p FROM Produto p WHERE p.estoqueAtual <= p.estoqueMinimo")
    List<Produto> findProdutosComEstoqueBaixo();

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}

@Repository
interface ProdutoEstoqueMovRepository extends JpaRepository<ProdutoEstoqueMov, String> {

    List<ProdutoEstoqueMov> findByIdProduto(String idProduto);

    List<ProdutoEstoqueMov> findByIdProdutoOrderByDataMovDesc(String idProduto);

    List<ProdutoEstoqueMov> findByTipoMov(String tipoMov);
}