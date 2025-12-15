package com.hubunity.core.domain.empresa;

import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.pessoas.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EmpresaRepository empresaRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public EmpresaResponse criar(EmpresaRequest request) {
        if (request.getIdSituacao() == null) {
            throw new IllegalArgumentException("ID da Situação é obrigatório.");
        }
        Empresa empresa = new Empresa();

        empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));

        if (request.getIdPessoa() != null) {
            empresa.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        }

        Empresa saved = empresaRepository.save(empresa);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public EmpresaResponse atualizar(String id, EmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        empresa.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        Empresa updated = empresaRepository.save(empresa);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<EmpresaResponse> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpresaResponse buscarPorId(String id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        return toResponse(empresa);
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if(!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada");
        }
        empresaRepository.deleteById(id);
    }


    // -------------------- RESPONSE -------------------- //

    private EmpresaResponse toResponse(Empresa empresa) {
        return new EmpresaResponse(
            empresa.getId(),
            empresa.getSituacao() != null ? empresa.getSituacao().getId() : null,
            empresa.getPessoa() != null ? empresa.getPessoa().getId() : null,
            empresa.getCreatedAt(),
            empresa.getUpdatedAt()
        );
    }


}
