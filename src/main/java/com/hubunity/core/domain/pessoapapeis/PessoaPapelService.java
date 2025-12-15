package com.hubunity.core.domain.pessoapapeis;

import com.hubunity.core.domain.dicpapeis.Papel;
import com.hubunity.core.domain.empresa.Empresa;
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
public class PessoaPapelService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PessoaPapelRepository pessoaPapelRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public PessoaPapelResponse criar(PessoaPapelRequest request) {
        PessoaPapel pessoaPapel = new PessoaPapel();

        pessoaPapel.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        pessoaPapel.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        pessoaPapel.setPapel(entityManager.getReference(Papel.class, request.getIdPapel()));

        PessoaPapel saved = pessoaPapelRepository.save(pessoaPapel);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public PessoaPapelResponse atualizar(String id, PessoaPapelRequest request) {
        PessoaPapel pessoaPapel = pessoaPapelRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Relação de Pessoa-Papel não encontrada"));

        pessoaPapel.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        pessoaPapel.setPessoa(entityManager.getReference(Pessoa.class, request.getIdPessoa()));
        pessoaPapel.setPapel(entityManager.getReference(Papel.class, request.getIdPapel()));

        PessoaPapel updated = pessoaPapelRepository.save(pessoaPapel);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public PessoaPapelResponse buscarPorId(String id) {
        PessoaPapel pessoaPapel = pessoaPapelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relação de Pessoa-Papel não encontrada"));
        return toResponse(pessoaPapel);
    }

    @Transactional(readOnly = true)
    public List<PessoaPapelResponse> listarTodos() {
        return pessoaPapelRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!pessoaPapelRepository.existsById(id)) {
            throw new RuntimeException("Relação de Pessoa-Papel não encontrada");
        }
        pessoaPapelRepository.deleteById(id);
    }


    // -------------------- RESPONSE -------------------- //

    private PessoaPapelResponse toResponse(PessoaPapel pessoaPapel) {
        return new PessoaPapelResponse(
            pessoaPapel.getId(),
            pessoaPapel.getEmpresa() != null ? pessoaPapel.getEmpresa().getId() : null,
            pessoaPapel.getPessoa() != null ? pessoaPapel.getPessoa().getId() : null,
            pessoaPapel.getPapel() != null ? pessoaPapel.getPapel().getId() : null,
            pessoaPapel.getCreatedAt()
        );
    }
}
