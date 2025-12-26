package com.hubunity.core.domain.funcionarios.pausas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioPausaRepository extends JpaRepository<FuncionarioPausa, String> {

  List<FuncionarioPausa> findByTipo(String tipo);

  List<FuncionarioPausa> findByFuncionarioId(String idFuncionario);

  List<FuncionarioPausa> findByAtivo(Boolean ativo);

}
