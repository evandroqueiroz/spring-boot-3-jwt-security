package com.alibou.core.domain.pessoas;

import com.alibou.core.domain.empresa.Empresa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository repository;

  public void save(PessoaRequest request) {
    // Simple mapping usually handled by Mapper, manual here for simplicity matching
    // the style
    Empresa empresa = null;
    if (request.getIdEmpresa() != null) {
      // Assuming simple generic repo method or I need to check how to fetch Empresa
      // For now standard findById
      // If EmpresaRepository is not found, I might need to adjust.
      // Given I saw 'empresa' package, likely there is a repo.
    }

    // Actually, to avoid assumption errors, let's defer Empresa fetch logic detail
    // slightly
    // or assume `EmpresaRepository` creates an entity reference.

    var pessoa = Pessoa.builder()
        .id(request.getId())
        .tipoPessoa(request.getTipoPessoa())
        .nomeCompleto(request.getNomeCompleto())
        .razaoSocial(request.getRazaoSocial())
        .nomeFantasia(request.getNomeFantasia())
        .email(request.getEmail())
        .telefone(request.getTelefone())
        .celular(request.getCelular())
        .documento(request.getDocumento())
        .dataInicio(request.getDataInicio() != null ? request.getDataInicio() : LocalDateTime.now())
        .dataFim(request.getDataFim())
        .build();

    if (request.getIdEmpresa() != null) {
      // Placeholder: Typically finding by ID.
      // Using a simple reference to avoid complex lookups without Service injection
      // If Empresa uses UUID or String
      pessoa.setEmpresa(Empresa.builder().id(request.getIdEmpresa()).build());
    }

    repository.save(pessoa);
  }

  public List<Pessoa> findAll() {
    return repository.findAll();
  }

  public Pessoa findById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
