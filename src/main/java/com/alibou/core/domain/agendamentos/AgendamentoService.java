package com.alibou.core.domain.agendamentos;

import com.alibou.core.domain.al_produtos.Produto;
import com.alibou.core.domain.al_servicos.Servico;
import com.alibou.core.domain.pessoas.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

  private final AgendamentoRepository repository;

  public void save(AgendamentoRequest request) {
    // Here we build the entity. References (Pessoa, Servico, Produto) are built
    // with IDs only to avoid fetching.
    // In a real scenario, we might want to validate existence.

    var agendamentoBuilder = Agendamento.builder()
        .id(request.getId())
        .dataHoraInicio(request.getDataHoraInicio())
        .dataHoraFim(request.getDataHoraFim())
        .status(request.getStatus() != null ? request.getStatus() : "PENDENTE")
        .precoFinal(request.getPrecoFinal())
        .observacoes(request.getObservacoes());

    if (request.getIdCliente() != null) {
      agendamentoBuilder.cliente(Pessoa.builder().id(request.getIdCliente()).build());
    }
    if (request.getIdProfissional() != null) {
      agendamentoBuilder.profissional(Pessoa.builder().id(request.getIdProfissional()).build());
    }
    if (request.getIdServico() != null) {
      agendamentoBuilder.servico(Servico.builder().id(request.getIdServico()).build());
    }
    if (request.getIdProdutoVinculado() != null) {
      agendamentoBuilder.produtoVinculado(Produto.builder().id(request.getIdProdutoVinculado()).build());
    }

    repository.save(agendamentoBuilder.build());
  }

  public List<Agendamento> findAll() {
    return repository.findAll();
  }

  public Agendamento findById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
