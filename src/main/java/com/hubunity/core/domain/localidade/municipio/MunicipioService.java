package com.hubunity.core.domain.localidade.municipio;

import com.hubunity.core.domain.localidade.estado.Estado;
import com.hubunity.core.domain.localidade.estado.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MunicipioService {

  private final MunicipioRepository municipioRepository;
  private final EstadoRepository estadoRepository;

  @Transactional
  public MunicipioResponse criar(MunicipioRequest request) {
    Estado estado = estadoRepository.findById(request.getIdEstado())
        .orElseThrow(() -> new RuntimeException("Estado não encontrado"));

    Municipio municipio = new Municipio();
    municipio.setEstado(estado);
    municipio.setNome(request.getNome());
    municipio.setCodigoIbge(request.getCodigoIbge());

    Municipio saved = municipioRepository.save(municipio);
    return toResponse(saved);
  }

  @Transactional
  public MunicipioResponse atualizar(String id, MunicipioRequest request) {
    Municipio municipio = municipioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Município não encontrado"));

    if (!municipio.getEstado().getId().equals(request.getIdEstado())) {
      Estado estado = estadoRepository.findById(request.getIdEstado())
          .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
      municipio.setEstado(estado);
    }

    municipio.setNome(request.getNome());
    municipio.setCodigoIbge(request.getCodigoIbge());

    Municipio updated = municipioRepository.save(municipio);
    return toResponse(updated);
  }

  @Transactional(readOnly = true)
  public List<MunicipioResponse> listarTodos() {
    return municipioRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public MunicipioResponse buscarPorId(String id) {
    Municipio municipio = municipioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Município não encontrado"));
    return toResponse(municipio);
  }

  @Transactional
  public void deletar(String id) {
    if (!municipioRepository.existsById(id)) {
      throw new RuntimeException("Município não encontrado");
    }
    municipioRepository.deleteById(id);
  }

  private MunicipioResponse toResponse(Municipio municipio) {
    return new MunicipioResponse(
        municipio.getId(),
        municipio.getEstado().getId(),
        municipio.getNome(),
        municipio.getCodigoIbge());
  }
}
