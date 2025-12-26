package com.hubunity.core.domain.horariotrabalho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, String> {

  List<HorarioTrabalho> findByFuncionarioId(String idFuncionario);

  List<HorarioTrabalho> findByDiaSemana(Integer diaSemana);

  List<HorarioTrabalho> findByAtivo(Boolean ativo);

  List<HorarioTrabalho> findByFuncionarioIdAndDiaSemana(String idFuncionario, Integer diaSemana);
}
