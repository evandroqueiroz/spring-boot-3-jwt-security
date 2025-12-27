package com.hubunity.core.domain.localidade.pais;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;

    @Transactional
    public PaisResponse criar(PaisRequest request) {
        Pais pais = new Pais();
        pais.setNome(request.getNome());
        pais.setSigla(request.getSigla());

        Pais saved = paisRepository.save(pais);
        return toResponse(saved);
    }

    @Transactional
    public PaisResponse atualizar(String id, PaisRequest request) {
        Pais pais = paisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));

        pais.setNome(request.getNome());
        pais.setSigla(request.getSigla());

        Pais updated = paisRepository.save(pais);
        return toResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<PaisResponse> listarTodos() {
        return paisRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaisResponse buscarPorId(String id) {
        Pais pais = paisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));
        return toResponse(pais);
    }

    @Transactional
    public void deletar(String id) {
        if (!paisRepository.existsById(id)) {
            throw new RuntimeException("País não encontrado");
        }
        paisRepository.deleteById(id);
    }

    private PaisResponse toResponse(Pais pais) {
        return new PaisResponse(
                pais.getId(),
                pais.getNome(),
                pais.getSigla()
        );
    }
}
