package com.hubunity.core.domain.geografia.cep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CepService {

  private final CepRepository repository;

  public Cep salvar(Cep cep) {
    return repository.save(cep);
  }

  public Cep atualizar(Cep cep) {
    return repository.save(cep);
  }

  public List<Cep> buscarTodos() {
    return repository.findAll();
  }

  public Cep buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("CEP não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
