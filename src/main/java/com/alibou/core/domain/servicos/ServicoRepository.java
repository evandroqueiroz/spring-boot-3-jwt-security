package com.alibou.core.domain.servicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, String> {

    Optional<Servico> findByCodigo(Long codigo);

    List<Servico> findByCategoria(String categoria);

    List<Servico> findByIdSituacao(String idSituacao);

    List<Servico> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT s FROM Servico s WHERE s.precoPromocional IS NOT NULL")
    List<Servico> findServicosComPromocao();
}

@Repository
interface ServicoExecucaoRepository extends JpaRepository<ServicoExecucao, String> {

    List<ServicoExecucao> findByIdServico(String idServico);

    List<ServicoExecucao> findByIdServicoOrderByDataExecucaoDesc(String idServico);

    @Query("SELECT se FROM ServicoExecucao se WHERE se.dataExecucao BETWEEN :dataInicio AND :dataFim")
    List<ServicoExecucao> findByPeriodo(java.util.Date dataInicio, java.util.Date dataFim);
}

@Repository
interface ServicoProdutoRepository extends JpaRepository<ServicoProduto, String> {

    List<ServicoProduto> findByIdServico(String idServico);

    List<ServicoProduto> findByIdProduto(String idProduto);

    Optional<ServicoProduto> findByIdServicoAndIdProduto(String idServico, String idProduto);

    void deleteByIdServico(String idServico);
}
