package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BairroService {

  private final BairroRepository repository;

  public Bairro salvar(Bairro bairro) {
    return repository.save(bairro);
  }

  public Bairro atualizar(Bairro bairro) {
    return repository.save(bairro);
  }

  public List<Bairro> buscarTodos() {
    return repository.findAll();
  }

  public Bairro buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Bairro não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
