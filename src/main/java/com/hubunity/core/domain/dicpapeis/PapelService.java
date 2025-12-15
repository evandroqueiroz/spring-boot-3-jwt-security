package com.hubunity.core.domain.dicpapeis;


import com.hubunity.core.domain.dicsituacao.Situacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PapelService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PapelRepository papelRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public PapelResponse criar(PapelRequest request) {
        Papel papel = new Papel();

        papel.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        papel.setNome(request.getNome());
        papel.setDescricao(request.getDescricao());

        Papel saved = papelRepository.save(papel);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public PapelResponse atualizar(String id, PapelRequest request) {
        Papel papel = papelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Papel não encontrado"));

        papel.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        papel.setNome(request.getNome());
        papel.setDescricao(request.getDescricao());

        Papel updated = papelRepository.save(papel);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public PapelResponse buscarPorId(String id) {
        Papel papel = papelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Papel não encontrado"));
        return toResponse(papel);
    }

    @Transactional(readOnly = true)
    public List<PapelResponse> listarTodos() {
        return papelRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!papelRepository.existsById(id)) {
            throw new RuntimeException("Papel não encontrado");
        }
        papelRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private PapelResponse toResponse(Papel papel) {
        return new PapelResponse(
                papel.getId(),
                papel.getSituacao() != null ? papel.getSituacao().getId() : null,
                papel.getNome(),
                papel.getDescricao(),
                papel.getCreatedAt()
        );
    }
}
