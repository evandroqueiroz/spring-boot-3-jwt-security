package com.hubunity.core.domain.core.servicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, String> {

    List<Servico> findByCategoria(String categoria);

    @Query("SELECT s FROM Servico s WHERE s.precoPromocional IS NOT NULL")
    List<Servico> findServicosComPromocao();
}

@Repository
interface ServicoExecucaoRepository extends JpaRepository<ServicoExecucao, String> {}

@Repository
interface ServicoProdutoRepository extends JpaRepository<ServicoProduto, String> {

    List<ServicoProduto> findByServico_Id(String idServico);

    Optional<ServicoProduto> findByServico_IdAndProduto_Id(String idServico, String idProduto);

}
