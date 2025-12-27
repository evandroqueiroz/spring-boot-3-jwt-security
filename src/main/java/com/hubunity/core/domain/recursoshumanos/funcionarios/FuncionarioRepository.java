package com.hubunity.core.domain.recursoshumanos.funcionarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {

    List<Funcionario> findByFuncao(String funcao);


}
