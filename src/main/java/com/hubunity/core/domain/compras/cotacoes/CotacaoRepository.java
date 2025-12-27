package com.hubunity.core.domain.compras.cotacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, String> {

  List<Cotacao> findAllByEmpresaId(String idEmpresa);

  List<Cotacao> findByEmpresaIdAndSituacaoId(String idEmpresa, String idSituacao);

  List<Cotacao> findByEmpresaIdAndFornecedorId(String idEmpresa, String idFornecedor);

  Optional<Cotacao> findByIdAndEmpresaId(String id, String idEmpresa);
}
