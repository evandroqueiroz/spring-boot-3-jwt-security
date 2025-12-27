package com.hubunity.core.domain.core.unidademedida;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnidadeMedidaService {

    private final UnidadeMedidaRepository unidadeMedidaRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public UnidadeMedidaResponse criar(UnidadeMedidaRequest request) {
        UnidadeMedida unidadeMedida = new UnidadeMedida();

        unidadeMedida.setNome(request.getNome());
        unidadeMedida.setCodigo(request.getCodigo());

        UnidadeMedida saved = unidadeMedidaRepository.save(unidadeMedida);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public UnidadeMedidaResponse atualizar(String id, UnidadeMedidaRequest request) {
        UnidadeMedida unidadeMedida = unidadeMedidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de Medida não encontrada"));

        unidadeMedida.setNome(request.getNome());
        unidadeMedida.setCodigo(request.getCodigo());

        UnidadeMedida updated = unidadeMedidaRepository.save(unidadeMedida);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<UnidadeMedidaResponse> buscarTodos() {
        return unidadeMedidaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnidadeMedidaResponse buscarPorId(String id) {
        UnidadeMedida unidadeMedida = unidadeMedidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de Medida não encontrada"));
        return toResponse(unidadeMedida);
    }

    @Transactional(readOnly = true)
    public List<UnidadeMedidaResponse> buscarPorCodigo(String codigo) {
        return unidadeMedidaRepository.findByCodigo(codigo).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UnidadeMedidaResponse> buscarPorNome(String nome) {
        return unidadeMedidaRepository.findByNome(nome).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!unidadeMedidaRepository.existsById(id)) {
            throw new RuntimeException("Unidade de Medida não encontrada");
        }
        unidadeMedidaRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private UnidadeMedidaResponse toResponse(UnidadeMedida unidadeMedida) {
        return new UnidadeMedidaResponse(
                unidadeMedida.getId(),
                unidadeMedida.getCodigo(),
                unidadeMedida.getNome()
        );
    }

}
