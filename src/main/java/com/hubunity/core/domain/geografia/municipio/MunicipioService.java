package com.hubunity.core.domain.geografia.municipio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipioService {

  private final MunicipioRepository repository;

  public Municipio salvar(Municipio municipio) {
    return repository.save(municipio);
  }

  public Municipio atualizar(Municipio municipio) {
    return repository.save(municipio);
  }

  public List<Municipio> buscarTodos() {
    return repository.findAll();
  }

  public Municipio buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Município não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
