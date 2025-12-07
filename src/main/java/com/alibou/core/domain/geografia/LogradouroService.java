package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogradouroService {

  private final LogradouroRepository repository;

  public Logradouro salvar(Logradouro logradouro) {
    return repository.save(logradouro);
  }

  public Logradouro atualizar(Logradouro logradouro) {
    return repository.save(logradouro);
  }

  public List<Logradouro> buscarTodos() {
    return repository.findAll();
  }

  public Logradouro buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Logradouro não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
