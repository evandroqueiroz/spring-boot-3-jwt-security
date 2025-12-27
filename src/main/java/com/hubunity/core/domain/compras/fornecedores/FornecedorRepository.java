package com.hubunity.core.domain.compras.fornecedores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {
  Optional<Fornecedor> findByPessoaId(String idPessoa);
}
