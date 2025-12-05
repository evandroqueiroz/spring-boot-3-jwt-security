package com.alibou.core.domain.empresa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;

    public Empresa salvar(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa atualizar(Empresa empresa) {
        return repository.save(empresa);
    }

    public List<Empresa> buscarTodasAsEmpresas() {
        return repository.findAll();
    }

    public Empresa buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }

}
