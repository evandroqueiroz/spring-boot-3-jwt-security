package com.hubunity.core.domain.agendamentos;

import com.hubunity.core.domain.clientes.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.dicsituacao.SituacaoRepository;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.funcionarios.FuncionarioRepository;
import com.hubunity.core.domain.pessoas.PessoaRepository;
import com.hubunity.core.domain.servicos.ServicoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AgendamentoRepository repository;
    private final FuncionarioRepository funcionarioRepository;
    private final PessoaRepository pessoaRepository;
    private final ServicoRepository servicoRepository;
    private final SituacaoRepository situacaoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public AgendamentoResponse create(AgendamentoRequest request) {
        var funcionario = funcionarioRepository.findById(request.getIdFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        var cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        var servico = servicoRepository.findById(request.getIdServico())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        var situacao = situacaoRepository
                .findById(request.getIdSituacao() != null ? request.getIdSituacao() : "A")
                .orElseThrow(() -> new IllegalArgumentException("Situação não encontrada"));

        var agendamento = new Agendamento();
        agendamento.setEmpresa(getEmpresaFromTenant());
        agendamento.setCliente(cliente);
        agendamento.setFuncionario(funcionario);
        agendamento.setServico(servico);
        agendamento.setSituacao(situacao);
        agendamento.setDataAgendamento(request.getDataAgendamento());
        agendamento.setObservacao(request.getObservacao());

        Agendamento saved = repository.save(agendamento);
        return toResponse(saved);
    }

    @Transactional
    public AgendamentoResponse atualizar(String id, AgendamentoRequest request) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        var funcionario = funcionarioRepository.findById(request.getIdFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        var cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        var servico = servicoRepository.findById(request.getIdServico())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        var situacao = situacaoRepository
                .findById(request.getIdSituacao() != null ? request.getIdSituacao() : "A")
                .orElseThrow(() -> new IllegalArgumentException("Situação não encontrada"));

        agendamento.setEmpresa(getEmpresaFromTenant());
        agendamento.setCliente(cliente);
        agendamento.setFuncionario(funcionario);
        agendamento.setServico(servico);
        agendamento.setSituacao(situacao);
        agendamento.setDataAgendamento(request.getDataAgendamento());
        agendamento.setObservacao(request.getObservacao());

        Agendamento updated = repository.save(agendamento);
        return toResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendamentoResponse buscarPorId(String id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        return toResponse(agendamento);
    }

    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        repository.deleteById(id);
    }

    private AgendamentoResponse toResponse(Agendamento entity) {
        return new AgendamentoResponse(
                entity.getId(),
                entity.getEmpresa() != null ? entity.getEmpresa().getId() : null,
                entity.getCliente().getId(),
                entity.getCliente().getPessoa().getNomeCompleto(),
                entity.getFuncionario().getId(),
                entity.getFuncionario().getPessoa().getNomeCompleto(),
                entity.getServico().getId(),
                entity.getServico().getNome(),
                entity.getSituacao().getId(),
                entity.getSituacao().getNome(),
                entity.getDataAgendamento(),
                entity.getObservacao(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private Empresa getEmpresaFromTenant() {
        String idEmpresa = TenantContext.getCurrentTenant();

        if (idEmpresa == null || idEmpresa.isBlank()) {
            throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
        }

        return entityManager.getReference(Empresa.class, idEmpresa);
    }

}
