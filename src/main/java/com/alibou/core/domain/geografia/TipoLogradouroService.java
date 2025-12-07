package com.alibou.core.domain.geografia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoLogradouroService {

  private final TipoLogradouroRepository repository;

  public TipoLogradouro salvar(TipoLogradouro tipoLogradouro) {
    return repository.save(tipoLogradouro);
  }

  public TipoLogradouro atualizar(TipoLogradouro tipoLogradouro) {
    return repository.save(tipoLogradouro);
  }

  public List<TipoLogradouro> buscarTodos() {
    return repository.findAll();
  }

  public TipoLogradouro buscarPorId(String id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de logradouro não encontrado"));
  }

  public void deletar(String id) {
    repository.deleteById(id);
  }
}
