package com.hubunity.core.domain.pessoas;

import com.hubunity.core.domain.dicsituacao.Situacao;
import com.hubunity.core.domain.empresa.Empresa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PessoaRepository pessoaRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public PessoaResponse criar(PessoaRequest request) {
        Pessoa pessoa = new Pessoa();

        pessoa.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        pessoa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        pessoa.setTipoPessoa(request.getTipoPessoa());
        pessoa.setRazaoSocial(request.getRazaoSocial());
        pessoa.setNomeFantasia(request.getNomeFantasia());
        pessoa.setNomeCompleto(request.getNomeCompleto());
        pessoa.setTipoDocumento(request.getTipoDocumento());
        pessoa.setDocumento(request.getDocumento());
        pessoa.setEmail(request.getEmail());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setCelular(request.getCelular());
        pessoa.setDataNascimento(request.getDataNascimento());

        Pessoa saved = pessoaRepository.save(pessoa);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public PessoaResponse atualizar(String id, PessoaRequest request) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        pessoa.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        pessoa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        pessoa.setTipoPessoa(request.getTipoPessoa());
        pessoa.setRazaoSocial(request.getRazaoSocial());
        pessoa.setNomeFantasia(request.getNomeFantasia());
        pessoa.setNomeCompleto(request.getNomeCompleto());
        pessoa.setTipoDocumento(request.getTipoDocumento());
        pessoa.setDocumento(request.getDocumento());
        pessoa.setEmail(request.getEmail());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setCelular(request.getCelular());
        pessoa.setDataNascimento(request.getDataNascimento());

        Pessoa updated = pessoaRepository.save(pessoa);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<PessoaResponse> listarTodos() {
        return pessoaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaResponse buscarPorId(String id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return toResponse(pessoa);
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if(!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private PessoaResponse toResponse(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getEmpresa() != null ? pessoa.getEmpresa().getId() : null,
                pessoa.getSituacao() != null ? pessoa.getSituacao().getId() : null,
                pessoa.getTipoPessoa(),
                pessoa.getRazaoSocial(),
                pessoa.getNomeFantasia(),
                pessoa.getNomeCompleto(),
                pessoa.getTipoDocumento(),
                pessoa.getDocumento(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getCelular(),
                pessoa.getDataNascimento(),
                pessoa.getCreatedAt(),
                pessoa.getUpdatedAt()
        );
    }

}
