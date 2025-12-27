package com.hubunity.core.domain.core.agendamentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, String> {

  List<Agendamento> findByFuncionarioIdAndDataAgendamentoBetween(String idFuncionario, LocalDateTime start,
      LocalDateTime end);

  List<Agendamento> findByClienteId(String idCliente);
}
