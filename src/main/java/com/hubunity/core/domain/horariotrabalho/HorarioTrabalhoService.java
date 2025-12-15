package com.hubunity.core.domain.horariotrabalho;

import com.hubunity.core.domain.empresa.EmpresaRepository;
import com.hubunity.core.domain.funcionarios.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioTrabalhoService {

  @Autowired
  private HorarioTrabalhoRepository repository;

  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Transactional
  public HorarioTrabalhoResponse create(HorarioTrabalhoRequest request) {
    var funcionario = funcionarioRepository.findById(request.getIdFuncionario())
        .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

    var horario = new HorarioTrabalho();
    horario.setIdEmpresa(request.getIdEmpresa());
    horario.setFuncionario(funcionario);
    horario.setDiaSemana(request.getDiaSemana());
    horario.setHoraInicio(request.getHoraInicio());
    horario.setHoraFim(request.getHoraFim());
    horario.setIntervaloInicio(request.getIntervaloInicio());
    horario.setIntervaloFim(request.getIntervaloFim());
    horario.setAtivo(request.getAtivo());

    repository.save(horario);
    return toResponse(horario);
  }

  public List<HorarioTrabalhoResponse> listByFuncionario(String idFuncionario) {
    return repository.findByFuncionarioId(idFuncionario).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private HorarioTrabalhoResponse toResponse(HorarioTrabalho entity) {
    return new HorarioTrabalhoResponse(
        entity.getId(),
        entity.getIdEmpresa(),
        entity.getFuncionario().getId(),
        entity.getFuncionario().getPessoa().getNomeCompleto(), // Assuming Funcionario has Pessoa
        entity.getDiaSemana(),
        entity.getHoraInicio(),
        entity.getHoraFim(),
        entity.getIntervaloInicio(),
        entity.getIntervaloFim(),
        entity.getAtivo());
  }
}
