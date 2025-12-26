package com.hubunity.core.domain.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {

    List<Produto> findByEmpresa_Id(String idEmpresa);

    Optional<Produto> findByCodigo(Long codigo);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findBySituacao_Id(String idSituacao);

    @Query("SELECT p FROM Produto p WHERE p.estoqueAtual <= p.estoqueMinimo")
    List<Produto> findProdutosComEstoqueBaixo();

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
