package com.hubunity.core.domain.clientes;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.pessoas.Pessoa;
import com.hubunity.core.domain.pessoas.PessoaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

  @PersistenceContext
  private EntityManager entityManager;

  private final ClienteRepository clienteRepository;
  private final PessoaRepository pessoaRepository;

  @Transactional
  public ClienteResponse criar(ClienteRequest request) {
    Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Cliente cliente = new Cliente();
    cliente.setEmpresa(getEmpresaFromTenant());
    cliente.setPessoa(pessoa);

    Cliente saved = clienteRepository.save(cliente);
    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<ClienteResponse> listarTodos() {
    return clienteRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ClienteResponse buscarPorId(String id) {
    Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    return toResponse(cliente);
  }

  @Transactional
  public void deletar(String id) {
    if (!clienteRepository.existsById(id)) {
      throw new RuntimeException("Cliente não encontrado");
    }
    clienteRepository.deleteById(id);
  }

  private ClienteResponse toResponse(Cliente cliente) {
    return new ClienteResponse(
        cliente.getId(),
        cliente.getEmpresa() != null ? cliente.getEmpresa().getId() : null,
        cliente.getPessoa() != null ? cliente.getPessoa().getId() : null,
        cliente.getPessoa() != null ? cliente.getPessoa().getNomeCompleto() : null,
        cliente.getPessoa() != null ? cliente.getPessoa().getDocumento() : null,
        cliente.getCreatedAt(),
        cliente.getUpdatedAt());
  }

  private Empresa getEmpresaFromTenant() {
    String idEmpresa = TenantContext.getCurrentTenant();
    if (idEmpresa == null || idEmpresa.isBlank()) {
      throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
    }
    return entityManager.getReference(Empresa.class, idEmpresa);
  }
}
