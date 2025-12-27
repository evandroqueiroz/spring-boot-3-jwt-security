package com.hubunity.core.domain.localidade.estado;

import com.hubunity.core.domain.localidade.pais.Pais;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EstadoRepository estadoRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public EstadoResponse criar(EstadoRequest request) {
        Estado estado = new Estado();
        estado.setPais(entityManager.getReference(Pais.class, request.getIdPais()));
        estado.setNome(request.getNome());
        estado.setSigla(request.getSigla());

        Estado saved = estadoRepository.save(estado);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public EstadoResponse atualizar(String id, EstadoRequest request) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado não encontrado"));

        estado.setPais(entityManager.getReference(Pais.class, request.getIdPais()));
        estado.setNome(request.getNome());
        estado.setSigla(request.getSigla());

        Estado updated = estadoRepository.save(estado);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<EstadoResponse> listarTodos() {
        return estadoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoResponse buscarPorId(String id) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
        return toResponse(estado);
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!estadoRepository.existsById(id)) {
            throw new RuntimeException("Estado não encontrado");
        }
        estadoRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private EstadoResponse toResponse(Estado estado) {
        return new EstadoResponse(
                estado.getId(),
                estado.getPais() != null ? estado.getPais().getId() : null,
                estado.getNome(),
                estado.getSigla()
        );
    }

}
