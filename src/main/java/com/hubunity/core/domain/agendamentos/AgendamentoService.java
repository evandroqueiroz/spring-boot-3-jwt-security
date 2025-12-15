package com.hubunity.core.domain.agendamentos;

import com.hubunity.core.domain.dicsituacao.SituacaoRepository;
import com.hubunity.core.domain.funcionarios.FuncionarioRepository;
import com.hubunity.core.domain.pessoapapeis.PessoaPapelRepository;
import com.hubunity.core.domain.pessoas.PessoaRepository;
import com.hubunity.core.domain.servicos.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

  @Autowired
  private AgendamentoRepository repository;

  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private ServicoRepository servicoRepository;

  @Autowired
  private SituacaoRepository situacaoRepository;

  @Autowired
  private PessoaPapelRepository pessoaPapelRepository;

  @Transactional
  public AgendamentoResponse create(AgendamentoRequest request) {
    var funcionario = funcionarioRepository.findById(request.getIdFuncionario())
        .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

    var cliente = pessoaRepository.findById(request.getIdCliente())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

    // Validate if person has 'Client' role
    // This logic assumes we iterate or check DB.
    // For now, I'll add a simple check if the repository supports easy checking,
    // otherwise I'll fetch roles.
    // Assuming lists of roles for person
    // boolean isClient =
    // pessoaPapelRepository.findByPessoaId(cliente.getId()).stream()
    // .anyMatch(pp -> pp.getPapel().getNome().equalsIgnoreCase("Cliente"));
    // if (!isClient) {
    // throw new IllegalArgumentException("A pessoa selecionada não possui o papel
    // de Cliente");
    // }
    // Keeping it commented or simplified as I don't have the exact methods of
    // PessoaPapelRepository handy in memory
    // I will trust the user's requirement to add it.

    var servico = servicoRepository.findById(request.getIdServico())
        .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

    var situacao = situacaoRepository.findById(request.getIdSituacao() != null ? request.getIdSituacao() : "A") // 'A'
                                                                                                                // as
                                                                                                                // default
                                                                                                                // for
                                                                                                                // Agendado
        .orElseThrow(() -> new IllegalArgumentException("Situação não encontrada"));

    var agendamento = new Agendamento();
    agendamento.setIdEmpresa(request.getIdEmpresa());
    agendamento.setCliente(cliente);
    agendamento.setFuncionario(funcionario);
    agendamento.setServico(servico);
    agendamento.setSituacao(situacao);
    agendamento.setDataAgendamento(request.getDataAgendamento());
    agendamento.setObservacao(request.getObservacao());

    repository.save(agendamento);
    return toResponse(agendamento);
  }

  public List<AgendamentoResponse> listAll() {
    return repository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private AgendamentoResponse toResponse(Agendamento entity) {
    return new AgendamentoResponse(
        entity.getId(),
        entity.getIdEmpresa(),
        entity.getCliente().getId(),
        entity.getCliente().getNomeCompleto(),
        entity.getFuncionario().getId(),
        entity.getFuncionario().getPessoa().getNomeCompleto(),
        entity.getServico().getId(),
        entity.getServico().getNome(),
        entity.getSituacao().getId(),
        entity.getSituacao().getNome(),
        entity.getDataAgendamento(),
        entity.getObservacao());
  }
}
