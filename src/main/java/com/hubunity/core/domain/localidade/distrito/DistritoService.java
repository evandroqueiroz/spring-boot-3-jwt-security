package com.hubunity.core.domain.localidade.distrito;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistritoService {

  private final DistritoRepository repository;

  public Distrito salvar(Distrito distrito) {
    return repository.save(distrito);
  }

  public Distrito atualizar(Distrito distrito) {
    return repository.save(distrito);
  }

  public List<Distrito> buscarTodos() {
    return repository.findAll();
  }

  public Distrito buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Distrito não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
