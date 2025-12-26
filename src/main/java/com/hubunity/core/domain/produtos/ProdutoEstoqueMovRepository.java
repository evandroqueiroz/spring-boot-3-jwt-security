package com.hubunity.core.domain.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoEstoqueMovRepository extends JpaRepository<ProdutoEstoqueMov, String> {

  List<ProdutoEstoqueMov> findByProduto_Id(String idProduto);

  @Query("SELECT m FROM ProdutoEstoqueMov m WHERE m.empresa.id = :idEmpresa AND m.tipoMov = :tipoMov")
  List<ProdutoEstoqueMov> buscarPorEmpresaETipo(@Param("idEmpresa") String idEmpresa, @Param("tipoMov") String tipoMov);

  @Query("SELECT m FROM ProdutoEstoqueMov m WHERE m.empresa.id = :idEmpresa")
  List<ProdutoEstoqueMov> buscarPorEmpresa(@Param("idEmpresa") String idEmpresa);
}
