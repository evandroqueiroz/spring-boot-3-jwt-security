package com.hubunity.core.domain.recursoshumanos.funcionarios;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.core.pessoas.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FuncionarioRepository funcionarioRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest request) {
        Funcionario funcionario = new Funcionario();

        funcionario.setEmpresa(getEmpresaFromTenant());
        funcionario.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        funcionario.setFuncao(request.getFuncao());
        funcionario.setSalario(request.getSalario());
        funcionario.setDataAdmissao(request.getDataAdmissao());

        Funcionario saved = funcionarioRepository.save(funcionario);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public FuncionarioResponse atualizar(String id, FuncionarioRequest request) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionario.setEmpresa(getEmpresaFromTenant());
        funcionario.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        funcionario.setFuncao(request.getFuncao());
        funcionario.setSalario(request.getSalario());
        funcionario.setDataAdmissao(request.getDataAdmissao());

        Funcionario updated = funcionarioRepository.save(funcionario);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(String id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return toResponse(funcionario);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> buscarPorFuncao(String funcao) {
        return funcionarioRepository.findByFuncao(funcao).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado");
        }
        funcionarioRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private FuncionarioResponse toResponse(Funcionario funcionario) {
        return new FuncionarioResponse(
            funcionario.getId(),
            funcionario.getEmpresa() != null ? funcionario.getEmpresa().getId() : null,
            funcionario.getPessoa() != null ? funcionario.getPessoa().getId() : null,
            funcionario.getFuncao(),
            funcionario.getSalario(),
            funcionario.getDataAdmissao()
        );
    }

    private Empresa getEmpresaFromTenant() {
        String idEmpresa = TenantContext.getCurrentTenant();

        if (idEmpresa == null || idEmpresa.isBlank()) {
            throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
        }

        return entityManager.getReference(Empresa.class, idEmpresa);
    }

}

