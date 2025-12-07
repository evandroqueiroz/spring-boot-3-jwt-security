package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalidadeService {

  private final LocalidadeRepository repository;

  public Localidade salvar(Localidade localidade) {
    return repository.save(localidade);
  }

  public Localidade atualizar(Localidade localidade) {
    return repository.save(localidade);
  }

  public List<Localidade> buscarTodos() {
    return repository.findAll();
  }

  public Localidade buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Localidade não encontrada"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
