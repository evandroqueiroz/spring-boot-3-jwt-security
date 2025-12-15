package com.hubunity.core.domain.geografia.estado;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

  private final EstadoRepository repository;

  public Estado salvar(Estado estado) {
    return repository.save(estado);
  }

  public Estado atualizar(Estado estado) {
    return repository.save(estado);
  }

  public List<Estado> buscarTodos() {
    return repository.findAll();
  }

  public Estado buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Estado não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
