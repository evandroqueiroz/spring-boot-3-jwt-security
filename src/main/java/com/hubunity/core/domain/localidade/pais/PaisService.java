package com.hubunity.core.domain.localidade.pais;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaisService {

  private final PaisRepository repository;

  public Pais salvar(Pais pais) {
    return repository.save(pais);
  }

  public Pais atualizar(Pais pais) {
    return repository.save(pais);
  }

  public List<Pais> buscarTodos() {
    return repository.findAll();
  }

  public Pais buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("País não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
