package com.hubunity.core.domain.core.empresa;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.localidade.endereco.Endereco;
import com.hubunity.core.domain.localidade.endereco.EnderecoResponseDTO;
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
    public EmpresaResponseDTO criar(EmpresaRequest request) {
        if (request.getIdSituacao() == null) {
            throw new IllegalArgumentException("ID da Situação é obrigatório.");
        }
        if (request.getIdEndereco() == null) {
            throw new IllegalArgumentException("ID do Endereço é obrigatório.");
        }
        if (request.getRazaoSocial() == null || request.getRazaoSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Razão Social é obrigatória.");
        }
        if (request.getDocumento() == null || request.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("Documento é obrigatório.");
        }

        Empresa empresa = new Empresa();

        empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        empresa.setEndereco(entityManager.getReference(Endereco.class, request.getIdEndereco()));
        empresa.setRazaoSocial(request.getRazaoSocial());
        empresa.setNomeFantasia(request.getNomeFantasia());
        empresa.setDocumento(request.getDocumento());
        empresa.setEmail(request.getEmail());
        empresa.setTelefone(request.getTelefone());

        Empresa saved = empresaRepository.save(empresa);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public EmpresaResponseDTO atualizar(String id, EmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        if (request.getIdSituacao() != null) {
            empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        }
        if (request.getIdEndereco() != null) {
            empresa.setEndereco(entityManager.getReference(Endereco.class, request.getIdEndereco()));
        }
        if (request.getRazaoSocial() != null) {
            empresa.setRazaoSocial(request.getRazaoSocial());
        }
        if (request.getNomeFantasia() != null) {
            empresa.setNomeFantasia(request.getNomeFantasia());
        }
        if (request.getDocumento() != null) {
            empresa.setDocumento(request.getDocumento());
        }
        if (request.getEmail() != null) {
            empresa.setEmail(request.getEmail());
        }
        if (request.getTelefone() != null) {
            empresa.setTelefone(request.getTelefone());
        }

        Empresa updated = empresaRepository.save(empresa);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorId(String id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        return toResponse(empresa);
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada");
        }
        empresaRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private EmpresaResponseDTO toResponse(Empresa empresa) {
        return EmpresaResponseDTO.builder()
                .id(empresa.getId())
                .idSituacao(empresa.getSituacao() != null ? empresa.getSituacao().getId() : null)
                .endereco(mapEndereco(empresa.getEndereco()))
                .razaoSocial(empresa.getRazaoSocial())
                .nomeFantasia(empresa.getNomeFantasia())
                .documento(empresa.getDocumento())
                .email(empresa.getEmail())
                .telefone(empresa.getTelefone())
                .createdAt(empresa.getCreatedAt())
                .updatedAt(empresa.getUpdatedAt())
                .build();
    }

    private EnderecoResponseDTO mapEndereco(Endereco endereco) {
        if (endereco == null)
            return null;

        return EnderecoResponseDTO.builder()
                .id(endereco.getId())
                .idMunicipio(endereco.getMunicipio() != null ? endereco.getMunicipio().getId() : null)
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cep(endereco.getCep())
                .build();
    }

}
