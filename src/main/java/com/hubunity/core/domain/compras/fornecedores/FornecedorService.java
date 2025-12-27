package com.hubunity.core.domain.compras.fornecedores;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.core.pessoas.Pessoa;
import com.hubunity.core.domain.core.pessoas.PessoaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

  @PersistenceContext
  private EntityManager entityManager;

  private final FornecedorRepository fornecedorRepository;
  private final PessoaRepository pessoaRepository;

  @Transactional
  public FornecedorResponse criar(FornecedorRequest request) {
    Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Fornecedor fornecedor = new Fornecedor();
    fornecedor.setEmpresa(getEmpresaFromTenant());
    fornecedor.setPessoa(pessoa);

    Fornecedor saved = fornecedorRepository.save(fornecedor);
    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<FornecedorResponse> listarTodos() {
    return fornecedorRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public FornecedorResponse buscarPorId(String id) {
    Fornecedor fornecedor = fornecedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
    return toResponse(fornecedor);
  }

  @Transactional
  public void deletar(String id) {
    if (!fornecedorRepository.existsById(id)) {
      throw new RuntimeException("Fornecedor não encontrado");
    }
    fornecedorRepository.deleteById(id);
  }

  private FornecedorResponse toResponse(Fornecedor fornecedor) {
    return new FornecedorResponse(
        fornecedor.getId(),
        fornecedor.getEmpresa() != null ? fornecedor.getEmpresa().getId() : null,
        fornecedor.getPessoa() != null ? fornecedor.getPessoa().getId() : null,
        fornecedor.getPessoa() != null ? fornecedor.getPessoa().getNomeCompleto() : null,
        fornecedor.getPessoa() != null ? fornecedor.getPessoa().getDocumento() : null,
        fornecedor.getCreatedAt(),
        fornecedor.getUpdatedAt());
  }

  private Empresa getEmpresaFromTenant() {
    String idEmpresa = TenantContext.getCurrentTenant();
    if (idEmpresa == null || idEmpresa.isBlank()) {
      throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
    }
    return entityManager.getReference(Empresa.class, idEmpresa);
  }
}
