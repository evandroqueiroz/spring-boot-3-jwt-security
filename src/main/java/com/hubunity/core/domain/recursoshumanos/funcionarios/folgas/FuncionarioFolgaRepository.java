package com.hubunity.core.domain.recursoshumanos.funcionarios.folgas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioFolgaRepository extends JpaRepository<FuncionarioFolga, String> {

  List<FuncionarioFolga> findByTipo(String tipo);

  List<FuncionarioFolga> findByFuncionarioId(String idFuncionario);

  List<FuncionarioFolga> findByAprovado(Boolean aprovado);

}
